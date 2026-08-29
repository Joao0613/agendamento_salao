package com.joao.agendamento.ui;

import com.joao.agendamento.service.ClienteService;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * SRP: apenas coleta os dados do formulario e chama ClienteService.
 * Nenhuma validacao de regra de negocio acontece aqui (fica no service).
 */
public class TelaCadastroCliente extends JDialog {

    private static final AtomicInteger PROXIMO_ID = new AtomicInteger(1);

    public TelaCadastroCliente(Frame owner, ClienteService clienteService) {
        super(owner, "Novo Cliente", true);
        setSize(320, 220);
        setLocationRelativeTo(owner);
        setLayout(new GridLayout(5, 2, 5, 5));

        JTextField campoNome = new JTextField();
        JTextField campoTelefone = new JTextField();
        JTextField campoEmail = new JTextField();
        JCheckBox checkFiel = new JCheckBox();
        JButton btnSalvar = new JButton("Salvar");

        add(new JLabel("Nome:"));
        add(campoNome);
        add(new JLabel("Telefone:"));
        add(campoTelefone);
        add(new JLabel("Email:"));
        add(campoEmail);
        add(new JLabel("Cliente fiel?"));
        add(checkFiel);
        add(new JLabel());
        add(btnSalvar);

        btnSalvar.addActionListener(e -> {
            try {
                clienteService.cadastrar(PROXIMO_ID.getAndIncrement(), campoNome.getText(),
                        campoTelefone.getText(), campoEmail.getText(), checkFiel.isSelected());
                JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");
                dispose();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de validacao", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
