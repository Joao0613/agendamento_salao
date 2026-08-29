package com.joao.agendamento.ui;

import com.joao.agendamento.model.Agendamento;
import com.joao.agendamento.service.AgendamentoService;
import com.joao.agendamento.service.ClienteService;
import com.joao.agendamento.service.ProfissionalService;
import com.joao.agendamento.service.ServicoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Tela principal do sistema desktop (Swing).
 *
 * SRP: esta classe cuida apenas de apresentacao (montar a janela,
 * abrir os dialogos, exibir a tabela de agendamentos). Nenhuma regra
 * de negocio (calculo de desconto, verificacao de conflito, validacao)
 * fica aqui - tudo delegado aos services, que sao recebidos prontos
 * (injecao de dependencia) via construtor.
 */
public class TelaPrincipal extends JFrame {

    private final ClienteService clienteService;
    private final ProfissionalService profissionalService;
    private final ServicoService servicoService;
    private final AgendamentoService agendamentoService;

    private final DefaultTableModel modeloTabela;

    public TelaPrincipal(ClienteService clienteService,
                          ProfissionalService profissionalService,
                          ServicoService servicoService,
                          AgendamentoService agendamentoService) {
        super("Salao - Sistema de Agendamento");
        this.clienteService = clienteService;
        this.profissionalService = profissionalService;
        this.servicoService = servicoService;
        this.agendamentoService = agendamentoService;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(720, 420);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel painelBotoes = new JPanel();
        JButton btnCliente = new JButton("Novo Cliente");
        JButton btnProfissional = new JButton("Novo Profissional");
        JButton btnServico = new JButton("Novo Servico");
        JButton btnAgendar = new JButton("Novo Agendamento");
        painelBotoes.add(btnCliente);
        painelBotoes.add(btnProfissional);
        painelBotoes.add(btnServico);
        painelBotoes.add(btnAgendar);
        add(painelBotoes, BorderLayout.NORTH);

        modeloTabela = new DefaultTableModel(
                new Object[]{"ID", "Cliente", "Profissional", "Servico", "Data/Hora", "Status"}, 0);
        JTable tabela = new JTable(modeloTabela);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        btnCliente.addActionListener(e ->
                new TelaCadastroCliente(this, clienteService).setVisible(true));
        btnProfissional.addActionListener(e ->
                new TelaCadastroProfissional(this, profissionalService).setVisible(true));
        btnServico.addActionListener(e ->
                new TelaCadastroServico(this, servicoService).setVisible(true));
        btnAgendar.addActionListener(e -> {
            new TelaAgendamento(this, clienteService, profissionalService, servicoService, agendamentoService)
                    .setVisible(true);
            atualizarTabela();
        });

        atualizarTabela();
    }

    public void atualizarTabela() {
        modeloTabela.setRowCount(0);
        List<Agendamento> agendamentos = agendamentoService.listarTodos();
        for (Agendamento a : agendamentos) {
            modeloTabela.addRow(new Object[]{
                    a.getId(), a.getCliente().getNome(), a.getProfissional().getNome(),
                    a.getServico().getNome(), a.getDataHora(), a.getStatus()
            });
        }
    }
}
