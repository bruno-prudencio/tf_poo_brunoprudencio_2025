import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.ArrayList;
import java.util.List;

public class FileExplorerApp extends JFrame { // ----- BLOCO 1 -----
    
    // atributos
    private JTree arvoreDiretorios; 
    private JTable tabelaArquivos;
    private JMenuItem criarItem;
    private JMenuItem deletarItem;
    
    // simulação de dados
    private List<ArquivoSimulado> dataModel = new ArrayList<>();
    private String currentPath = "Raiz"; // simula o caminho do diretório atual

    // construtor
    public FileExplorerApp() { // -----BLOCO 2 -----
        setTitle("Navegador de Arquivos POO");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());
        
        initializeDataModel(); // Carrega os dados simulados
        setupSplitPane(); 
        setupMenuBar(); 
        addTreeSelectionListener(); 
        
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    // carrega o modelo de dados (ArrayList)
    private void initializeDataModel() { // ---- BLOCO 3 -----

        // estrutura Simples de Pastas
        dataModel.add(new ArquivoSimulado("Pasta A", "dir", "dir", "Raiz", true));
    dataModel.add(new ArquivoSimulado("Pasta B", "dir", "dir", "Raiz", true));
    dataModel.add(new ArquivoSimulado("Pasta C", "dir", "dir", "Raiz", true));

    // arquivos dentro da raíz
    dataModel.add(new ArquivoSimulado("projeto_POO.txt", "txt", "10 KB", "Raiz", false));
    dataModel.add(new ArquivoSimulado("requisitos.pdf", "pdf", "512 KB", "Raiz", false));

    // arquivos dentro da pasta "A" (pra simular navegação)
    dataModel.add(new ArquivoSimulado("documento_1.docx", "docx", "200 KB", "Pasta A", false));
    }
    
    private void setupSplitPane() { // ----- BLOCO 4 -----
        // estrutura do JTree (lado esquerdo do app)
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(currentPath);
        
        // cria nós com base no dataModel
        dataModel.stream()
            .filter(ArquivoSimulado::isDirectory)
            .filter(a -> a.getParentPath().equals(currentPath))
            .forEach(a -> {
                DefaultMutableTreeNode dirNode = new DefaultMutableTreeNode(a);
                // Adiciona um nó teste para permitir a expansão visual
                dirNode.add(new DefaultMutableTreeNode("teste")); 
                root.add(dirNode);
            });
        
        arvoreDiretorios = new JTree(root); // lado esquerdo
        arvoreDiretorios.setShowsRootHandles(true);
        arvoreDiretorios.setRootVisible(true); 
        JScrollPane treeScrollPane = new JScrollPane(arvoreDiretorios);
        String[] columnNames = {"Nome do Arquivo", "Tipo", "Tamanho"};
        DefaultTableModel tableModel = new DefaultTableModel(null, columnNames);

        tabelaArquivos = new JTable(tableModel); // lado direito
        JScrollPane tableScrollPane = new JScrollPane(tabelaArquivos);
        JSplitPane dividirPainel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treeScrollPane, tableScrollPane);
        dividirPainel.setDividerLocation(200); 
        add(dividirPainel, BorderLayout.CENTER);

        listarArquivos(currentPath); // carrega o conteúdo a partir da raiz ao iniciar
    }
    
    // construtor da barra de menu com as interações de nova pasta e excluir pasta
    private void setupMenuBar() {  // ----- BLOCO 5 -----
        JMenuBar menuBar = new JMenuBar(); // cria a barra
        JMenu fileMenu = new JMenu("Arquivo");
        
        criarItem = new JMenuItem("Nova Pasta"); //botão nova pasta
        deletarItem = new JMenuItem("Excluir"); // botão excluir 
        JMenuItem settingsItem = new JMenuItem("Configurações (cosmético)"); //configurações apenas para mostrar
        
        fileMenu.add(criarItem); 
        fileMenu.add(deletarItem);
        fileMenu.addSeparator();
        fileMenu.add(settingsItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar); // anexa toda essa barra a janela do jframe
        criarItem.addActionListener(e -> createNewFolder());
        deletarItem.addActionListener(e -> deleteSelectedFile());
    }
    
    // funciona como event listener, basicamente detecta o clique do usuário
    private void addTreeSelectionListener() { // ----- BLOCO 6 ----- 
        arvoreDiretorios.getSelectionModel().addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) arvoreDiretorios.getLastSelectedPathComponent();
            
            if (node == null || node.getUserObject() instanceof String) { //verifica se o nó é nulo ou se é apenas uma string
                // se for a raiz ou um nó teste
                if (node != null && node.getUserObject().equals(currentPath)) {
                    listarArquivos(currentPath);
                }
                return;
            }
            
            ArquivoSimulado selectedFile = (ArquivoSimulado) node.getUserObject();
            
            if (selectedFile.isDirectory()) {
                currentPath = selectedFile.getNome(); // define o novo caminho
                listarArquivos(currentPath);         // lista o conteúdo
            }
        });
    }

    // lista arquivos
    private void listarArquivos(String path) { // ----- BLOCO 7 -----
        DefaultTableModel tableModel = (DefaultTableModel) tabelaArquivos.getModel();
        tableModel.setRowCount(0);

        // filtração do dataModel pelos arquivos do caminho atual
        dataModel.stream() // garante que vai exibir apenas os arquivos que pertencem a pasta selecionada
            .filter(a -> a.getParentPath().equals(path))
            .forEach(file -> {
                tableModel.addRow(new Object[]{
                    file.getNome(), 
                    file.isDirectory() ? "dir" : file.getTipo(), 
                    file.getTamanho(),
                });
            });
    }
    
    // cria uma pasta na arraylist
    private void createNewFolder() { // ----- BLOCO 8 -----
        String dirPai = currentPath; //pega o nome da pastga atual, onde a nova vai ser criada
        String folderName = JOptionPane.showInputDialog(this, //popup para digitar o nome da nova pasta
            "Digite o nome da nova pasta (Local: " + dirPai + "):", 
            "Nova Pasta", JOptionPane.PLAIN_MESSAGE);
        
        if (folderName == null || folderName.trim().isEmpty()) return;
        
        // verifica se a pasta já existe
        boolean exists = dataModel.stream()
            .anyMatch(a -> a.getNome().equalsIgnoreCase(folderName) && a.getParentPath().equals(dirPai));

        if (exists) {
            JOptionPane.showMessageDialog(this, "A pasta já existe neste local (Simulado).", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // cria e adiciona um arquivo na ArrayList
        ArquivoSimulado newFolder = new ArquivoSimulado(
            folderName, 
            "dir", 
            "dir",
            dirPai, 
            true 
        );
        dataModel.add(newFolder);
        
        // atualiza a GUI
        JOptionPane.showMessageDialog(this, "Pasta SIMULADA criada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        listarArquivos(dirPai); 
        //força o reload da raíz
        ((DefaultTreeModel) arvoreDiretorios.getModel()).setRoot(buildSimulatedTree(currentPath));
    }

    // exclui uma pasta do arraylist
    private void deleteSelectedFile() { // ----- BLOCO 9 -----
        int selectedRow = tabelaArquivos.getSelectedRow();
        // verifica a linha selecionada
        if (selectedRow == -1) { //caso nenhuma seja selecionada, manda uma mensagem de aviso e retorna
            JOptionPane.showMessageDialog(this, "Selecione um arquivo ou pasta para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // identificação e confirmação do item selecionado.
        String fileNameToDelete = (String) tabelaArquivos.getValueAt(selectedRow, 0);
        String dirPai = currentPath;
        
        int confirm = JOptionPane.showConfirmDialog(this, //abre uma caixa de dialogo de sim ou não para confirmação
            "Exluir o item: " + fileNameToDelete + "?", 
            "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) { //se a resposta for sim, exclui a pasta do dataModel (ArrayList)
            
                // excluindo o arraylist
            boolean success = dataModel.removeIf(a -> 
                a.getNome().equalsIgnoreCase(fileNameToDelete) && a.getParentPath().equals(dirPai)); // verifica se o nome do objeto é igual ao selecionado e também se o objeto pertence a pasta atual.
            
            if (success) {
                // atualiza a gui
                JOptionPane.showMessageDialog(this, "Item excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                listarArquivos(dirPai); 
                
                // atualiza o jtree
                ((DefaultTreeModel) arvoreDiretorios.getModel()).setRoot(buildSimulatedTree(currentPath));
            } else {
                JOptionPane.showMessageDialog(this, "Falha ao excluir.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // recria o JTree simulado (auxiliar)
    private DefaultMutableTreeNode buildSimulatedTree(String rootPath) { // ----- BLOCO 10 -----
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootPath); //rotula e cria um nó como raíz da pasta que está sendo reconstruída
        
        dataModel.stream() //filtra a lista dos arquivos (apenas as pastas)
            .filter(ArquivoSimulado::isDirectory)
            .filter(a -> a.getParentPath().equals(rootPath))
            .forEach(a -> { //pra cada pasta que passa pelo filtro, cria um nodo filho
                DefaultMutableTreeNode dirNode = new DefaultMutableTreeNode(a);
                dirNode.add(new DefaultMutableTreeNode("teste")); //insere um nó teste, apenas pra demonstração
                root.add(dirNode);
            });
        return root; //retorna o raíz com seus filhos e  nós de demonstração prontos para serem anexados.
    }
    //main
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FileExplorerApp()); // usa o invokeLater (EDT) para garantir que o JFrame não trave
    }
}