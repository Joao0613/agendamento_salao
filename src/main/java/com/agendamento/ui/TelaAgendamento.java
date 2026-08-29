package com.joao.agendamento.ui;

import com.joao.agendamento.model.Cliente;
import com.joao.agendamento.model.Profissional;
import com.joao.agendamento.model.Servico;
import com.joao.agendamento.service.AgendamentoService;
import com.joao.agendamento.service.ClienteService;
import com.joao.agendamento.service.ProfissionalService;
import com.joao.agendamento.service.ServicoService;
import com.joao.agendamento.service.exception.HorarioIndisponivelException;
import java.util.function.Function;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * SRP: apenas monta o formulario de agendamento e delega toda a regra
 * de negocio (checagem de conflito, calculo de valor, notificacao)
 * para AgendamentoService.
 */
public class TelaAgendamento extends JDialog {

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public TelaAgendamento(Frame owner, ClienteService clienteService, ProfissionalService profissionalService,
                            ServicoService servicoService, AgendamentoService agendamentoService) {
        super(owner, "Novo Agendamento", true);
        setSize(380, 260);
        setLocationRelativeTo(owner);
        setLayout(new GridLayout(5, 2, 5, 5));

        JComboBox<Cliente> comboCliente = new JComboBox<>(clienteService.listarTodos().toArray(new Cliente[0]));
        JComboBox<Profissional> comboProfissional =
                new JComboBox<>(profissionalService.listarTodos().toArray(new Profissional[0]));
        JComboBox<Servico> comboServico = new JComboBox<>(servicoService.listarTodos().toArray(new Servico[0]));
        JTextField campoDataHora = new JTextField("dd/MM/yyyy HH:mm");
        comboCliente.setRenderer(new TextoAmigavelRenderer<Cliente>(c -> c.getNome()));
        comboProfissional.setRenderer(new TextoAmigavelRenderer<Profissional>(
                p -> p.getNome() + " (" + p.getEspecialidade() + ")"));
        comboServico.setRenderer(new TextoAmigavelRenderer<Servico>(
                s -> s.getNome() + " - R$ " + s.getPreco()));
        JButton btnAgendar = new JButton("Agendar");

        add(new JLabel("Cliente:"));
        add(comboCliente);
        add(new JLabel("Profissional:"));
        add(comboProfissional);
        add(new JLabel("Servico:"));
        add(comboServico);
        add(new JLabel("Data/Hora:"));
        add(campoDataHora);
        add(new JLabel());
        add(btnAgendar);

        btnAgendar.addActionListener(e -> {
            try {
                Cliente cliente = (Cliente) comboCliente.getSelectedItem();
                Profissional profissional = (Profissional) comboProfissional.getSelectedItem();
                Servico servico = (Servico) comboServico.getSelectedItem();
                LocalDateTime dataHora = LocalDateTime.parse(campoDataHora.getText().trim(), FORMATO);

                if (cliente == null || profissional == null || servico == null) {
                    JOptionPane.showMessageDialog(this, "Cadastre ao menos um cliente, profissional e servico antes.",
                            "Dados incompletos", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                agendamentoService.agendar(cliente, profissional, servico, dataHora);
                JOptionPane.showMessageDialog(this, "Agendamento realizado com sucesso!");
                dispose();
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Data/hora invalida. Use o formato dd/MM/yyyy HH:mm.",
                        "Erro de validacao", JOptionPane.ERROR_MESSAGE);
            } catch (HorarioIndisponivelException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Horario indisponivel",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }
        /**
     * Renderizador generico de itens de JComboBox: exibe apenas o texto
     * amigavel produzido pela funcao informada, em vez do toString()
     * padrao do objeto. SRP: unica responsabilidade e formatar a
     * exibicao do item selecionado/da lista, sem conhecer regra de
     * negocio nenhuma.
     */
    private static class TextoAmigavelRenderer<T> extends DefaultListCellRenderer {

        private final Function<T, String> formatador;

        TextoAmigavelRenderer(Function<T, String> formatador) {
            this.formatador = formatador;
        }

        @Override
        @SuppressWarnings("unchecked")
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                        boolean isSelected, boolean cellHasFocus) {
            String texto = value == null ? "" : formatador.apply((T) value);
            return super.getListCellRendererComponent(list, texto, index, isSelected, cellHasFocus);
        }
    }
}
