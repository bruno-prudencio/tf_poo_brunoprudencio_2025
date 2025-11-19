# tf_poo_brunoprudencio_2025

----- BLOCOS -----

1- Escolhi o uso do JFrame que é uma classe da biblioteca swing e serve justamente pra 
funcionar como uma janela.

2- No construtor eu implementei todas as funcionalidades e chamei o método que cria os dados simulados (ArquivoSimulado)

3- Cria os arquivos/pastas simuladas pra simular a navegação

4- Monta a divisão da estrutura, com a arvore de diretórios a esquerda e a tabela de arquivos a direita

5- Criação da barra de menu com as funcionalidades de criar e excluir uma pasta

6- É o controlador de eventos que detecta o clique do usuário em alguma pasta e muda o conteúdo exibido. Também tem validação de arquivos.

7- Filtra a lista de arquivos simulados (dataModel)

8- O código abre uma janela pop-up pra criar a pasta nova e também faz verificação no dataModel pra verificar se já existe um arquivo com o mesmo nome. Após criar, o objeto é adicionado ao dataModel. E depois faz um reload da raíz para atualizar a tabela.

9- Assim como o de criar, ele mexe apenas no dataModel, sem mexer com o sistema.
Garante que o item selecionado, após confirmação o usuário, seja excluido. E então, atualiza a interface, exibindo uma mensagem de sucesso. Então o JTree é recarregado
para a exclusão ficar visivel.

10- Usado pra reconstruir um ramo específico da JTree, chamado depois de cirar ou excluir alguma pasta e garante que a árvore  de navegação seja atualizada.
