public class ArquivoSimulado {
    
    private String nome;
    private String tipo; //"dir", "txt", "pdf"
    private String tamanho;
    private String parentPath; 
    private boolean isDirectory;

    // construtor
    public ArquivoSimulado(String nome, String tipo, String tamanho, String parentPath, boolean isDirectory) {
        this.nome = nome;
        this.tipo = tipo;
        this.tamanho = tamanho;
        this.parentPath = parentPath;
        this.isDirectory = isDirectory;
    }

    // Getters
    public String getNome() { 
        return nome; 
    }
    public String getTipo() {
        return tipo; 
    }
    public String getTamanho() { 
        return tamanho; 
    }
    public String getParentPath() {
        return parentPath; 
    }
    public boolean isDirectory() {
        return isDirectory; 
    }

    // Setters
    public void setNome(String nome) { this.nome = nome; }

    @Override
    public String toString() {
        return nome;
    }
}