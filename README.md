# PI - Sistema de Agendamento de Salao (Fase de Refatoracao SOLID)

Projeto Integrador (PI) - fase de refatoracao do sistema desktop,
aplicando principios SOLID (com enfase em SRP), refatoracoes e
padroes de projeto (Strategy).

## Como abrir no NetBeans

1. Abra o NetBeans.
2. Menu **File > Open Project**.
3. Selecione a pasta `pi-agendamento-salao` (o NetBeans reconhece
   automaticamente o `pom.xml` como projeto Maven).
4. Clique com o botao direito no projeto > **Run**, ou execute a
   classe `com.joao.agendamento.App` (metodo `main`).


## Estrutura de pacotes

- `model` - entidades de dominio (Cliente, Profissional, Servico, Agendamento)
- `repository` - interfaces de persistencia (DIP) + implementacao em memoria
- `service` - regras de negocio (SRP), politica de desconto (Strategy) e notificacao
- `ui` - telas Swing (somente apresentacao, sem regra de negocio)
- `legado` - classe historica mantida apenas para referencia no relatorio,
  mostrando os code smells da versao anterior (nao utilizada pelo sistema)

## Documentacao

Veja `/docs` para o documento de projeto e o relatorio de refatoracao
(principios SOLID aplicados, refatoracoes realizadas e padrao de
projeto utilizado).
