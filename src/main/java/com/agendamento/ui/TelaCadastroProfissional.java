package com.joao.agendamento.ui;

import com.joao.agendamento.service.ProfissionalService;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TelaCadastroProfissional extends JDialog {

    private static final AtomicInteger PROXIMO_ID = new AtomicInteger(1);

    public TelaCadastroProfissional(Frame owner, ProfissionalService profissionalService) {
        super(owner, "Novo Profissional", true);
        setSize(320, 160);
        setLocationRelativeTo(owner);
        setLayout(new GridLayout(3, 2, 5, 5));

        JTextField campoNome = new JTextField();
        JTextField campoEspecialidade = new JTextField();
        JButton btnSalvar = new JButton("Salvar");

        add(new JLabel("Nome:"));
        add(campoNome);
        add(new JLabel("Especialidade:"));
        add(campoEspecialidade);
        add(new JLabel());
        add(btnSalvar);

        btnSalvar.addActionListener(e -> {
            try {
                profissionalService.cadastrar(PROXIMO_ID.getAndIncrement(), campoNome.getText(),
                        campoEspecialidade.getText());
                JOptionPane.showMessageDialog(this, "Profissional cadastrado com sucesso!");
                dispose();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de validacao", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
