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
    
    // override do método toString para exibir informações da tarefa ao usuário
    @Override
    public String toString() {
        return "Id: " + id + 
        		"\nTítulo: " + titulo + 
        		"\nDescrição: " + descricao + 
        		"\nData: " + dataToString() + 
        		"\nData de Criação: " + dataDeCriacaoToString() + 
        		"\nCompleta: " + completa + "\n";
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
	public Tarefa(){
        completa = false;
    }
	
	// construtor com parâmetros
	public Tarefa(int id, String titulo, String descricao, LocalDateTime data, LocalDateTime dataDeCriacao, boolean completa) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descricao = descricao;
		this.data = data;
		this.dataDeCriacao = dataDeCriacao;
		this.completa = completa;
	}
	
	// getter e setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
    public void toggleCompleta(){
        if(this.completa == false){
            this.completa = true;
        }else{
            this.completa = false;
        }
    }
    public boolean isCompleta() {
        return completa;
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
    }
    
    // método para converter a data de string para o formato correto de LocalDateTime
    public LocalDateTime formatarData(String data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dataFormatada = null;
        
        try {
            dataFormatada = LocalDateTime.parse(data, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Erro ao formatar a data: " + e.getMessage());
            System.out.println("Formato esperado: dd/MM/yyyy HH:mm");
        }
        return dataFormatada;
    }
    
}
