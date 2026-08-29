package com.joao.agendamento.ui;

import com.joao.agendamento.service.ServicoService;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

public class TelaCadastroServico extends JDialog {

    private static final AtomicInteger PROXIMO_ID = new AtomicInteger(1);

    public TelaCadastroServico(Frame owner, ServicoService servicoService) {
        super(owner, "Novo Servico", true);
        setSize(320, 200);
        setLocationRelativeTo(owner);
        setLayout(new GridLayout(4, 2, 5, 5));

        JTextField campoNome = new JTextField();
        JTextField campoDuracao = new JTextField();
        JTextField campoPreco = new JTextField();
        JButton btnSalvar = new JButton("Salvar");

        add(new JLabel("Nome:"));
        add(campoNome);
        add(new JLabel("Duracao (min):"));
        add(campoDuracao);
        add(new JLabel("Preco (R$):"));
        add(campoPreco);
        add(new JLabel());
        add(btnSalvar);

        btnSalvar.addActionListener(e -> {
            try {
                int duracao = Integer.parseInt(campoDuracao.getText().trim());
                BigDecimal preco = new BigDecimal(campoPreco.getText().trim());
                servicoService.cadastrar(PROXIMO_ID.getAndIncrement(), campoNome.getText(), duracao, preco);
                JOptionPane.showMessageDialog(this, "Servico cadastrado com sucesso!");
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Duracao e preco devem ser numeros validos.",
                        "Erro de validacao", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de validacao", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
