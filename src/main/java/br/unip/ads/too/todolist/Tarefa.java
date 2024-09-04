package br.unip.ads.too.todolist;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Tarefa {
	private int id;
    private boolean completa;
    private String titulo;
    private String descricao;
    private LocalDateTime dataDeCriacao;
    private LocalDateTime data;
    private boolean atrasada;
    
    // override do método toString para exibir informações da tarefa ao usuário
    @Override
    public String toString() {
        return "Id: " + id + 
        		"\nTítulo: " + titulo + 
        		"\nDescrição: " + descricao + 
        		"\nData: " + dataToString() + 
        		"\nData de Criação: " + dataDeCriacaoToString() + 
        		"\nCompleta: " + completa +
        		"\nAtrasada: " + atrasada + "\n";
    }
    
    // método para converter a data para formato mais amigável para exibir ao usuário
	public String dataToString() {
		DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("EEEE, dd/MM/yyyy HH:mm");
        return data.format(displayFormatter);
    }
	
	// método para converter a data de criação para formato mais amigável para exibir ao usuário
	public String dataDeCriacaoToString() {
		DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		return dataDeCriacao.format(displayFormatter);
	}

    // construtor padrão
	public Tarefa(){ }
	
	// construtor com parâmetros
	public Tarefa(int id, String titulo, String descricao, LocalDateTime data, LocalDateTime dataDeCriacao, boolean completa) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descricao = descricao;
		this.data = data;
		this.dataDeCriacao = dataDeCriacao;
		this.completa = completa;
		verificarAtraso();
	}
	
	// getter e setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

    public boolean isCompleta() { // método get
        return completa;
    }
    public void toggleCompleta(){ // método set que alterna os valores
        if(this.completa == false){
            this.completa = true;
        }else{
            this.completa = false;
        }
    }
	
	public boolean isAtrasada() { // método get
		return atrasada;
	}
	public void verificarAtraso() { // método set que verifica se a tarefa está atrasada
		if (LocalDateTime.now().isAfter(data)) {
			this.atrasada = true;
		} else {
			this.atrasada = false;
		}
	}
    
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public LocalDateTime getDataDeCriacao() {
        return dataDeCriacao;
    }
    public void setDataDeCriacao() {
        this.dataDeCriacao = LocalDateTime.now().withNano(0);
    }
    
    public LocalDateTime getData() {
        return data;
    }
    public void setData(LocalDateTime data) {
        this.data = data;
        verificarAtraso();
    }
    
    // método para converter a data de string para o formato correto de LocalDateTime
    public LocalDateTime formatarData(String data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dataFormatada = null;
        
        try {
            dataFormatada = LocalDateTime.parse(data, formatter);
        } catch (DateTimeParseException e) {
            System.err.println("Erro ao formatar a data: " + e.getMessage());
            System.out.println("Erro ao formatar a data.");
            System.out.println("Formato esperado: dd/MM/yyyy HH:mm");
        }
        return dataFormatada;
    }
    
}
