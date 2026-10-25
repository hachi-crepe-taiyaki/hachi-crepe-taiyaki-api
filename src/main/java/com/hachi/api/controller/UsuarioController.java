package com.hachi.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.hachi.api.config.AutenticacaoFilter;
import com.hachi.api.config.GerenciadorTokenJwt;
import com.hachi.api.dto.CodigoResgateDTO;
import com.hachi.api.dto.FeedbackCadastroDTO;
import com.hachi.api.dto.LoginDTO;
import com.hachi.api.dto.ResultadoLoginDTO;
import com.hachi.api.dto.UsuarioAtualizacaoDTO;
import com.hachi.api.dto.UsuarioCadastroDTO;
import com.hachi.api.dto.UsuarioRespostaDTO;
import com.hachi.api.service.UsuarioService;
import com.hachi.api.service.ResgateSeloService;
import com.hachi.api.service.ResgateItemService;
import com.hachi.api.service.FeedbackService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Tag(name = "Usuários", description = "Cadastro, autenticação, gerenciamento de contas e operações do programa de fidelidade.")
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final ResgateSeloService resgateSeloService;
    private final ResgateItemService resgateItemService;
    private final FeedbackService feedbackService;
    private final boolean cookieSeguro;
    private final GerenciadorTokenJwt gerenciadorTokenJwt;

    public UsuarioController(UsuarioService usuarioService,
                             ResgateSeloService resgateSeloService,
                             ResgateItemService resgateItemService,
                             FeedbackService feedbackService,
                             GerenciadorTokenJwt gerenciadorTokenJwt,
                             @Value("${app.cookie.secure:false}") boolean cookieSeguro) {
        this.usuarioService = usuarioService;
        this.resgateSeloService = resgateSeloService;
        this.resgateItemService = resgateItemService;
        this.feedbackService = feedbackService;
        this.gerenciadorTokenJwt = gerenciadorTokenJwt;
        this.cookieSeguro = cookieSeguro;
    }

    @Operation(summary = "Cadastrar usuário", description = "Cria uma conta de cliente. A função é atribuída como cliente pelo servidor; a senha é armazenada usando BCrypt. O cadastro não exige autenticação.")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Conta criada."), @ApiResponse(responseCode = "400", description = "Dados obrigatórios inválidos ou fora das regras de validação."), @ApiResponse(responseCode = "409", description = "E-mail já cadastrado.")})
    @PostMapping
    public ResponseEntity<UsuarioRespostaDTO> cadastrarUsuario(@Valid @RequestBody UsuarioCadastroDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.cadastrarUsuario(dto));
    }

    @Operation(summary = "Listar usuários", description = "Retorna usuários que correspondem aos filtros opcionais. E-mail e nome aceitam correspondência parcial sem diferenciar maiúsculas de minúsculas; telefone aceita correspondência parcial; data de nascimento é exata. Requer autenticação.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Lista retornada, possivelmente vazia."), @ApiResponse(responseCode = "401", description = "Usuário não autenticado.")})
    @GetMapping
    public ResponseEntity<List<UsuarioRespostaDTO>> listarUsuarios(
            @Parameter(description = "Filtra por e-mail, aceitando trecho parcial.", example = "marley") @RequestParam(required = false) String email,
            @Parameter(description = "Filtra por telefone, aceitando trecho parcial.", example = "119876") @RequestParam(required = false) String telefone,
            @RequestParam(required = false)
            @Parameter(description = "Filtra pela data de nascimento exata no formato yyyy-MM-dd.", example = "2000-05-15") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dtNasc,
            @Parameter(description = "Filtra por cidade.", example = "São Paulo") @RequestParam(required = false) String cidade,
            @Parameter(description = "Filtra por região.", example = "Centro") @RequestParam(required = false) String regiao,
            @Parameter(description = "Filtra por nome, aceitando trecho parcial sem diferenciar maiúsculas de minúsculas.", example = "Mar") @RequestParam(required = false) String nome) {
        return ResponseEntity.ok(
                usuarioService.listarUsuarios(email, telefone, dtNasc, cidade, regiao, nome));
    }

    @Operation(summary = "Autenticar usuário", description = "Valida e-mail e senha. Em caso de sucesso, retorna os dados públicos do usuário e define o JWT no cookie HttpOnly hachi_access_token. A senha é comparada com o hash BCrypt armazenado; contas inativas não podem entrar.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Autenticação concluída; cookie de sessão enviado."), @ApiResponse(responseCode = "400", description = "Formato de requisição inválido."), @ApiResponse(responseCode = "401", description = "Credenciais inválidas ou conta inativa.")})
    @PostMapping("/login")
    public ResponseEntity<UsuarioRespostaDTO> autenticarUsuario(@Valid @RequestBody LoginDTO dto,
                                                                 HttpServletResponse resposta) {
        ResultadoLoginDTO resultado = usuarioService.autenticarUsuario(dto);
        resposta.addHeader("Set-Cookie", criarCookieToken(resultado.getToken(),
                Duration.ofMinutes(gerenciadorTokenJwt.obterValidadeMinutos())).toString());
        return ResponseEntity.ok(resultado.getUsuario());
    }

    @Operation(summary = "Encerrar sessão", description = "Expira o cookie hachi_access_token no navegador. O endpoint não exige um token válido para permitir limpar uma sessão local.")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Cookie de autenticação expirado.")})
    @PostMapping("/logout")
    public ResponseEntity<Void> encerrarSessao(HttpServletResponse resposta) {
        resposta.addHeader("Set-Cookie", criarCookieToken("", Duration.ZERO).toString());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Enviar feedback", description = "Registra um feedback para o usuário autenticado. O ID do usuário é obtido da autenticação, não do corpo da requisição. A nota deve estar entre 0 e 5 e o comentário deve respeitar as validações do DTO.")
    @SecurityRequirement(name = "cookieAuth")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Feedback registrado."), @ApiResponse(responseCode = "400", description = "Nota ou comentário inválido."), @ApiResponse(responseCode = "401", description = "Usuário não autenticado.")})
    @PostMapping("/feedback")
    public ResponseEntity<Void> cadastrarFeedback(@Valid @RequestBody FeedbackCadastroDTO dto,
                                                   Authentication autenticacao) {
        Integer idAutenticado = obterIdAutenticado(autenticacao);
        feedbackService.cadastrarFeedback(idAutenticado, dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Registrar selo de compra", description = "Resgata um código de compra de uso único e adiciona um selo à conta do usuário autenticado. O ID do caminho deve corresponder ao usuário autenticado; o código precisa existir e ainda não ter sido utilizado.")
    @SecurityRequirement(name = "cookieAuth")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Selo registrado."), @ApiResponse(responseCode = "400", description = "Código ausente ou inválido."), @ApiResponse(responseCode = "401", description = "Usuário não autenticado."), @ApiResponse(responseCode = "403", description = "Tentativa de registrar selo para outra conta."), @ApiResponse(responseCode = "404", description = "Usuário ou código não encontrado."), @ApiResponse(responseCode = "409", description = "Código já utilizado ou operação incompatível com o estado atual.")})
    @PostMapping("/{id}/selos")
    public ResponseEntity<Void> resgatarSelo(@Parameter(description = "ID do usuário alvo. Em operações de conta própria, deve corresponder ao usuário autenticado.", example = "1") @PathVariable Integer id,
                                              @Valid @RequestBody CodigoResgateDTO dto,
                                              Authentication autenticacao) {
        Integer idAutenticado = obterIdAutenticado(autenticacao);
        resgateSeloService.resgatarSelo(idAutenticado, id, dto.getCodigo());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Resgatar item grátis", description = "Resgata a recompensa do programa de fidelidade para o usuário autenticado. É necessário possuir pelo menos 12 selos; o sistema debita exatamente 12. O ID do caminho deve ser o da própria conta.")
    @SecurityRequirement(name = "cookieAuth")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Recompensa resgatada."), @ApiResponse(responseCode = "401", description = "Usuário não autenticado."), @ApiResponse(responseCode = "403", description = "Tentativa de resgatar para outra conta."), @ApiResponse(responseCode = "404", description = "Usuário não encontrado."), @ApiResponse(responseCode = "409", description = "Saldo de selos insuficiente ou usuário inativo.")})
    @PostMapping("/{id}/resgatarItem")
    public ResponseEntity<Void> resgatarItem(@PathVariable Integer id, Authentication autenticacao) {
        Integer idAutenticado = obterIdAutenticado(autenticacao);
        resgateItemService.resgatarItem(idAutenticado, id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualizar usuário", description = "Atualiza os dados permitidos da conta. O próprio usuário pode atualizar sua conta; um administrador também pode atualizar outra conta. Função, status e quantidade de selos não são alteráveis por este endpoint. Uma senha enviada é armazenada com BCrypt.")
    @SecurityRequirement(name = "cookieAuth")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Usuário atualizado."), @ApiResponse(responseCode = "400", description = "Dados inválidos."), @ApiResponse(responseCode = "401", description = "Usuário não autenticado."), @ApiResponse(responseCode = "403", description = "Sem permissão para atualizar esta conta."), @ApiResponse(responseCode = "404", description = "Usuário não encontrado."), @ApiResponse(responseCode = "409", description = "E-mail já utilizado por outra conta.")})
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioRespostaDTO> atualizarUsuario(@PathVariable Integer id,
            @Valid @RequestBody UsuarioAtualizacaoDTO dto, Authentication autenticacao) {
        Integer idAutenticado = obterIdAutenticado(autenticacao);
        boolean administrador = autenticacao.getAuthorities().stream()
                .anyMatch(autoridade -> autoridade.getAuthority().equals("ROLE_ADMIN"));
        return ResponseEntity.ok(usuarioService.atualizarUsuario(id, dto, idAutenticado, administrador));
    }

    @Operation(summary = "Excluir usuário", description = "Realiza exclusão lógica da conta, alterando seu status para inativo e preservando históricos de selos e feedbacks. O próprio usuário pode excluir a própria conta; um administrador pode excluir outras.")
    @SecurityRequirement(name = "cookieAuth")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Conta desativada."), @ApiResponse(responseCode = "401", description = "Usuário não autenticado."), @ApiResponse(responseCode = "403", description = "Sem permissão para excluir esta conta."), @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirUsuario(@PathVariable Integer id, Authentication autenticacao) {
        Integer idAutenticado = obterIdAutenticado(autenticacao);
        boolean administrador = autenticacao.getAuthorities().stream()
                .anyMatch(autoridade -> autoridade.getAuthority().equals("ROLE_ADMIN"));
        usuarioService.excluirUsuario(id, idAutenticado, administrador);
        return ResponseEntity.noContent().build();
    }

    private Integer obterIdAutenticado(Authentication autenticacao) {
        if (autenticacao == null || autenticacao.getName() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "É necessário estar autenticado.");
        }
        try {
            return Integer.valueOf(autenticacao.getName());
        } catch (NumberFormatException excecao) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "A autenticação é inválida.");
        }
    }

    private ResponseCookie criarCookieToken(String token, Duration duracao) {
        return ResponseCookie.from(AutenticacaoFilter.COOKIE_NOME, token)
                .httpOnly(true)
                .secure(cookieSeguro)
                .sameSite("Strict")
                .path("/")
                .maxAge(duracao)
                .build();
    }
}
