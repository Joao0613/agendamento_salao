package com.joao.agendamento;

import com.joao.agendamento.model.Agendamento;
import com.joao.agendamento.model.Cliente;
import com.joao.agendamento.model.Profissional;
import com.joao.agendamento.model.Servico;
import com.joao.agendamento.repository.AgendamentoRepository;
import com.joao.agendamento.repository.ClienteRepository;
import com.joao.agendamento.repository.ProfissionalRepository;
import com.joao.agendamento.repository.ServicoRepository;
import com.joao.agendamento.repository.memoria.AgendamentoRepositoryMemoria;
import com.joao.agendamento.repository.memoria.ClienteRepositoryMemoria;
import com.joao.agendamento.repository.memoria.ProfissionalRepositoryMemoria;
import com.joao.agendamento.repository.memoria.ServicoRepositoryMemoria;
import com.joao.agendamento.service.AgendamentoService;
import com.joao.agendamento.service.ClienteService;
import com.joao.agendamento.service.ProfissionalService;
import com.joao.agendamento.service.ServicoService;
import com.joao.agendamento.service.desconto.DescontoClienteFiel;
import com.joao.agendamento.service.desconto.PoliticaDesconto;
import com.joao.agendamento.service.exception.HorarioIndisponivelException;
import com.joao.agendamento.service.notificacao.Notificador;
import com.joao.agendamento.service.notificacao.NotificadorConsole;
import com.joao.agendamento.ui.TelaPrincipal;

import javax.swing.SwingUtilities;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Ponto de entrada do sistema.
 *
 * DIP em acao: e AQUI, na composicao da aplicacao (a unica "camada"
 * que conhece as implementacoes concretas), que os repositorios,
 * a politica de desconto e o notificador sao instanciados e injetados
 * nos services. As camadas de service e ui so conhecem interfaces.
 *
 * Antes de abrir a interface grafica, o metodo main() executa uma
 * bateria de testes automatizados simples (sem framework de testes,
 * conforme solicitado na atividade) para validar as principais regras
 * de negocio: cadastro, agendamento, deteccao de conflito de horario
 * e calculo de desconto.
 */
public class App {

    private static int totalTestes = 0;
    private static int testesOk = 0;

    public static void main(String[] args) {
        System.out.println("===== Executando testes automatizados do sistema =====");
        executarTestes();
        System.out.println("===== " + testesOk + "/" + totalTestes + " testes passaram =====\n");

        System.out.println("Iniciando interface grafica...");
        SwingUtilities.invokeLater(App::iniciarAplicacaoGrafica);
    }

    private static void iniciarAplicacaoGrafica() {
        ClienteRepository clienteRepository = new ClienteRepositoryMemoria();
        ProfissionalRepository profissionalRepository = new ProfissionalRepositoryMemoria();
        ServicoRepository servicoRepository = new ServicoRepositoryMemoria();
        AgendamentoRepository agendamentoRepository = new AgendamentoRepositoryMemoria();

        PoliticaDesconto politicaDesconto = new DescontoClienteFiel();
        Notificador notificador = new NotificadorConsole();

        ClienteService clienteService = new ClienteService(clienteRepository);
        ProfissionalService profissionalService = new ProfissionalService(profissionalRepository);
        ServicoService servicoService = new ServicoService(servicoRepository);
        AgendamentoService agendamentoService =
                new AgendamentoService(agendamentoRepository, politicaDesconto, notificador);

        // dados iniciais de exemplo, para facilitar a demonstracao na UI
        clienteService.cadastrar(1, "Maria Silva", "48999990000", "maria@email.com", true);
        clienteService.cadastrar(2, "Joana Souza", "48988880000", "joana@email.com", false);
        profissionalService.cadastrar(1, "Carla Mendes", "Cabeleireira");
        profissionalService.cadastrar(2, "Pedro Alves", "Manicure");
        servicoService.cadastrar(1, "Corte de Cabelo", 45, new BigDecimal("80.00"));
        servicoService.cadastrar(2, "Manicure", 30, new BigDecimal("50.00"));

        new TelaPrincipal(clienteService, profissionalService, servicoService, agendamentoService).setVisible(true);
    }

    // ---------------------------------------------------------------
    // Testes simples via metodo main(), conforme pedido na atividade.
    // ---------------------------------------------------------------

    private static void executarTestes() {
        ClienteRepository clienteRepository = new ClienteRepositoryMemoria();
        ProfissionalRepository profissionalRepository = new ProfissionalRepositoryMemoria();
        ServicoRepository servicoRepository = new ServicoRepositoryMemoria();
        AgendamentoRepository agendamentoRepository = new AgendamentoRepositoryMemoria();

        PoliticaDesconto politicaDesconto = new DescontoClienteFiel();
        Notificador notificador = new NotificadorConsole();

        ClienteService clienteService = new ClienteService(clienteRepository);
        ProfissionalService profissionalService = new ProfissionalService(profissionalRepository);
        ServicoService servicoService = new ServicoService(servicoRepository);
        AgendamentoService agendamentoService =
                new AgendamentoService(agendamentoRepository, politicaDesconto, notificador);

        Cliente clienteFiel = clienteService.cadastrar(1, "Maria Silva", "48999990000", "maria@email.com", true);
        Cliente clienteComum = clienteService.cadastrar(2, "Joana Souza", "48988880000", "joana@email.com", false);
        Profissional profissional = profissionalService.cadastrar(1, "Carla Mendes", "Cabeleireira");
        Servico corte = servicoService.cadastrar(1, "Corte de Cabelo", 45, new BigDecimal("80.00"));

        LocalDateTime horario = LocalDateTime.of(2026, 9, 1, 10, 0);

        // Teste 1: cadastro basico
        verificar("Cadastro de cliente", clienteService.listarTodos().size() == 2);

        // Teste 2: agendamento valido
        Agendamento agendamento1 = null;
        try {
            agendamento1 = agendamentoService.agendar(clienteFiel, profissional, corte, horario);
            verificar("Agendamento valido criado", agendamento1 != null);
        } catch (HorarioIndisponivelException ex) {
            verificar("Agendamento valido criado", false);
        }

        // Teste 3: desconto de cliente fiel (10% sobre R$80,00 = R$72,00)
        if (agendamento1 != null) {
            BigDecimal valorFinal = agendamentoService.calcularValorFinal(agendamento1);
            verificar("Desconto de cliente fiel aplicado corretamente",
                    valorFinal.compareTo(new BigDecimal("72.00")) == 0);
        }

        // Teste 4: conflito de horario deve lancar excecao
        boolean conflitoDetectado = false;
        try {
            agendamentoService.agendar(clienteComum, profissional, corte, horario);
        } catch (HorarioIndisponivelException ex) {
            conflitoDetectado = true;
        }
        verificar("Conflito de horario detectado", conflitoDetectado);

        // Teste 5: horario diferente para o mesmo profissional deve funcionar
        boolean segundoAgendamentoOk = true;
        try {
            agendamentoService.agendar(clienteComum, profissional, corte, horario.plusHours(2));
        } catch (HorarioIndisponivelException ex) {
            segundoAgendamentoOk = false;
        }
        verificar("Agendamento em horario livre aceito", segundoAgendamentoOk);

        // Teste 6: cliente sem fidelidade nao recebe desconto
        for (Agendamento a : agendamentoService.listarTodos()) {
            if (a.getCliente().equals(clienteComum)) {
                BigDecimal valorFinal = agendamentoService.calcularValorFinal(a);
                verificar("Cliente sem fidelidade paga valor cheio",
                        valorFinal.compareTo(new BigDecimal("80.00")) == 0);
                break;
            }
        }
    }

    private static void verificar(String descricaoTeste, boolean condicaoEsperadaVerdadeira) {
        totalTestes++;
        if (condicaoEsperadaVerdadeira) {
            testesOk++;
            System.out.println("[OK]    " + descricaoTeste);
        } else {
            System.out.println("[FALHOU] " + descricaoTeste);
        }
    }
}
