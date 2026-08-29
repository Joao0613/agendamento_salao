package com.joao.agendamento.legado;

import java.util.ArrayList;
import java.util.List;

/**
 * ===========================================================================
 * VERSAO INICIAL (ANTES DA REFATORACAO) - MANTIDA APENAS COMO REGISTRO
 * HISTORICO PARA O RELATORIO. NAO E UTILIZADA PELO SISTEMA ATUAL.
 * ===========================================================================
 *
 * Esta classe representa a primeira versao do sistema de agendamento,
 * escrita antes da aplicacao de SOLID e da refatoracao. Ela concentra
 * TODAS as responsabilidades do sistema em um unico lugar (God Class /
 * Blob), o que caracteriza varios code smells classicos:
 *
 * 1) God Class / Large Class: uma unica classe cuida de cadastro de
 *    cliente, cadastro de servico, agendamento, calculo de desconto,
 *    verificacao de conflito de horario, "persistencia" (listas em
 *    memoria) e ate simulacao de interface (prints no console).
 *
 * 2) Long Method: o metodo agendarServico() faz validacao, verificacao
 *    de conflito, calculo de preco com desconto e "notificacao" tudo
 *    junto, em dezenas de linhas.
 *
 * 3) Uso de campos publicos (Data Class / falta de encapsulamento):
 *    listas e atributos expostos diretamente, sem getters/setters,
 *    permitindo que qualquer parte do codigo altere o estado interno.
 *
 * 4) Numeros e strings magicos: percentual de desconto (0.1) e
 *    mensagens de erro escritos diretamente no meio da logica.
 *
 * 5) Uso de RuntimeException generica para erros de negocio (horario
 *    ocupado, cliente invalido), dificultando tratamento especifico
 *    pelo chamador.
 *
 * 6) Duplicacao de codigo: a logica de verificar se dois horarios se
 *    sobrepoem esta reescrita em mais de um lugar (aqui simplificada
 *    em um unico metodo para fins didaticos, mas na pratica o problema
 *    se repetia em telas diferentes que faziam a mesma checagem).
 *
 * Todos esses pontos foram corrigidos na versao atual, distribuida nos
 * pacotes model, repository, service e ui (ver pacote
 * com.joao.agendamento e o relatorio de refatoracao).
 */
public class SistemaAgendamentoAntigo {

    // Code smell: campos publicos, sem encapsulamento (Data Class).
    public List<String[]> clientes = new ArrayList<>();      // {id, nome, "S"/"N" fiel}
    public List<String[]> servicos = new ArrayList<>();       // {id, nome, duracao, preco}
    public List<String[]> agendamentos = new ArrayList<>();   // {id, clienteId, servicoId, profissionalId, dataHora}

    // Code smell: metodo longo com multiplas responsabilidades.
    public void agendarServico(String clienteId, String servicoId, String profissionalId, String dataHora) {
        // validacao misturada com regra de negocio
        boolean clienteExiste = false;
        boolean clienteFiel = false;
        for (String[] c : clientes) {
            if (c[0].equals(clienteId)) {
                clienteExiste = true;
                clienteFiel = c[2].equals("S");
            }
        }
        if (!clienteExiste) {
            // code smell: RuntimeException generica, mensagem magica
            throw new RuntimeException("erro: cliente invalido");
        }

        // verificacao de conflito de horario duplicada / colada aqui
        for (String[] ag : agendamentos) {
            if (ag[3].equals(profissionalId) && ag[4].equals(dataHora)) {
                throw new RuntimeException("erro: horario ocupado");
            }
        }

        // calculo de preco com numero magico (10% de desconto)
        double preco = 0;
        for (String[] s : servicos) {
            if (s[0].equals(servicoId)) {
                preco = Double.parseDouble(s[3]);
            }
        }
        if (clienteFiel) {
            preco = preco - (preco * 0.1);
        }

        agendamentos.add(new String[]{
                String.valueOf(agendamentos.size() + 1), clienteId, servicoId, profissionalId, dataHora
        });

        // "notificacao" misturada com regra de negocio, direto no console
        System.out.println("Agendado! Valor: " + preco);
    }
}
