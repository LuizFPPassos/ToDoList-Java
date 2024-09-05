package br.unip.ads.too.todolist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class ListaDeTarefas {

    private List<Tarefa> listaDeTarefas;
    private String titulo;
    private static final String NOME_ARQUIVO_JSON_DEFAULT = "ListaDeTarefas";
    private static final String NOME_ARQUIVO_CONFIG = "config.cfg";
    private String nomeArquivoJson = "ListaDeTarefas";

    // getters e setters
    public List<Tarefa> getListaDeTarefas() {
        return listaDeTarefas;
    }
    public void setListaDeTarefas(List<Tarefa> listaDeTarefas) {
        this.listaDeTarefas = listaDeTarefas;
    }

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNomeArquivoJson() {
        return nomeArquivoJson;
    }
    public void setNomeArquivoJson(String nomeArquivoJson) {
        this.nomeArquivoJson = nomeArquivoJson;
    }

    // método para obter o próximo Id disponível
    public int obterProximoId() {
        int proximoId = 1;
        for (Tarefa tarefa : listaDeTarefas) {
            if (tarefa.getId() >= proximoId) {
                proximoId = tarefa.getId() + 1;
            }
        }
        return proximoId;
    }

    // método para excluir uma tarefa por Id
    public void excluirTarefa(int id) {
        listaDeTarefas.removeIf(tarefa -> tarefa.getId() == id);
        System.out.println("Tarefa excluída com sucesso.");
    }

    // método para editar uma tarefa
    public void editarTarefa(Tarefa tarefaEditar) {
        for (Tarefa tarefa : listaDeTarefas) {
            if (tarefa.getId() == tarefaEditar.getId()) {
                tarefa.setTitulo(tarefaEditar.getTitulo());
                tarefa.setDescricao(tarefaEditar.getDescricao());
                tarefa.setData(tarefaEditar.getData());
                System.out.println("Tarefa editada com sucesso.");
                return;
            }
        }
    }

    // método para procurar uma tarefa por Id
    public Tarefa procurarTarefa(int id) {
        for (Tarefa tarefa : listaDeTarefas) {
            if (tarefa.getId() == id) {
                return tarefa;
            }
        }
        return null;
    }

    // método para exportar a lista de tarefas para um arquivo JSON
    public void exportTarefasToJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // ativa formatação com indentação para legibilidade
        objectMapper.registerModule(new JavaTimeModule());

        ListaDeTarefasComTitulo listaComTitulo = new ListaDeTarefasComTitulo(titulo, listaDeTarefas);

        try {
            objectMapper.writeValue(new File(nomeArquivoJson + ".json"), listaComTitulo);
            System.out.println("Lista de tarefas salva em: " + nomeArquivoJson + ".json");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao exportar lista de tarefas para JSON");
        }
    }

    // método para ordenar a lista de tarefas por data
    public void ordenarListaDeTarefas() {
        listaDeTarefas.sort(Comparator
            .comparing(Tarefa::getData) // primariamente por data
            .thenComparing(Tarefa::getDataDeCriacao)); // desempate por data de criação
    }

    // método para carregar a lista de tarefas a partir de um arquivo JSON
    public void carregarListaDeTarefas() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        File file = new File(nomeArquivoJson + ".json");

        if (!file.exists()) {
            try {
                System.out.println("Arquivo " + nomeArquivoJson + ".json não encontrado. Criando novo arquivo...");

                setTitulo("Minha Lista de Tarefas"); // título padrão

                file.createNewFile();

                try (FileWriter writer = new FileWriter(file)) {
                    writer.write("{ \"titulo\": \"" + getTitulo() + "\", \"listaDeTarefas\": [] }");
                    writer.flush();
                }
                System.out.println("Arquivo " + nomeArquivoJson + ".json criado com sucesso.");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Erro ao criar arquivo " + nomeArquivoJson + ".json");
                return;
            }
        }

        try {
            ListaDeTarefasComTitulo listaComTitulo = objectMapper.readValue(file, ListaDeTarefasComTitulo.class);
            this.listaDeTarefas = listaComTitulo.getListaDeTarefas();
            this.titulo = listaComTitulo.getTitulo();
            System.out.println("Lista de tarefas carregada com sucesso");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar lista de tarefas");
        }
    }

    // método para criar uma nova lista de tarefas em um arquivo JSON
    public void criarNovaListaDeTarefas() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        File file = new File(nomeArquivoJson + ".json");

        try {
            file.createNewFile();

            try (FileWriter writer = new FileWriter(file)) {
                writer.write("{ \"titulo\": \"" + getTitulo() + "\", \"listaDeTarefas\": [] }");
                writer.flush();
            }
            System.out.println("Arquivo " + nomeArquivoJson + ".json criado com sucesso.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao criar arquivo " + nomeArquivoJson + ".json");
            return;
        }

        try {
            ListaDeTarefasComTitulo listaComTitulo = objectMapper.readValue(file, ListaDeTarefasComTitulo.class);
            this.listaDeTarefas = listaComTitulo.getListaDeTarefas();
            this.titulo = listaComTitulo.getTitulo();
            System.out.println("Lista de tarefas carregada com sucesso");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar lista de tarefas");
        }
    }

    // método para atualizar a lista de tarefas (ordenar e exportar)
    public void atualizarListaDeTarefas() {
        ordenarListaDeTarefas();
        exportTarefasToJSON();
    }

    // método para obter a última lista de tarefas utilizada
    public void obterUltimaListaDeTarefas() {
        File file = new File(NOME_ARQUIVO_CONFIG);

        if (file.exists()) {
            try {
                List<String> linhas = java.nio.file.Files.readAllLines(file.toPath());

                for (String linha : linhas) {
                    if (linha.startsWith("ultimaListaUtilizada=")) { // chave que define o nome do ultimo arquivo JSON utilizado
                        String nomeArquivo = linha.split("=")[1].trim();
                        System.out.println("Arquivo de configuração carregado com sucesso");

                        if (verificarArquivoJsonExiste(nomeArquivo)) {
                            System.out.println("Arquivo " + nomeArquivoJson + ".json" + " encontrado.");
                            setNomeArquivoJson(nomeArquivo);
                        } else {
                            System.out.println("Arquivo " + nomeArquivoJson + ".json" + " não encontrado.");
                            resetarNomeArquivoJson();
                        }

                        System.out.println("Utilizando arquivo " + nomeArquivoJson + ".json" + " para salvar a lista de tarefas.");
                        return;
                    }
                }
                System.out.println("Chave 'ultimaListaUtilizada' não encontrada no arquivo de configuração.");
                resetarNomeArquivoJson();

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Erro ao carregar arquivo de configuração");
                resetarNomeArquivoJson();
            }
        } else {
            System.out.println("Arquivo de configuração não encontrado.");
            resetarNomeArquivoJson();
        }
    }

    // método para salvar a última lista de tarefas utilizada em um arquivo de configuração
    public void salvarUltimaListaDeTarefas() {
        File file = new File(NOME_ARQUIVO_CONFIG);

        try (FileWriter writer = new FileWriter(file)) {
            writer.write("ultimaListaUtilizada=" + nomeArquivoJson + "\n");
            writer.flush();
            System.out.println("Arquivo de configuração salvo com sucesso");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao salvar arquivo de configuração");
        }
    }

    // método para verificar se um arquivo JSON existe
    public boolean verificarArquivoJsonExiste(String nomeArquivoJson) {
        File file = new File(nomeArquivoJson + ".json");
        return file.exists();
    }

    // método para resetar o nome do arquivo JSON para o padrão
    public void resetarNomeArquivoJson() {
        System.out.println("Utilizando arquivo padrão " + NOME_ARQUIVO_JSON_DEFAULT + ".");
        nomeArquivoJson = NOME_ARQUIVO_JSON_DEFAULT;
        salvarUltimaListaDeTarefas(); // salvar o arquivo de configuração
    }
    
}

// classe para encapsular o título e a lista de tarefas
class ListaDeTarefasComTitulo {

    private String titulo;
    private List<Tarefa> listaDeTarefas;

    // construtores
    public ListaDeTarefasComTitulo() { }

    public ListaDeTarefasComTitulo(String titulo, List<Tarefa> listaDeTarefas) {
        this.titulo = titulo;
        this.listaDeTarefas = listaDeTarefas;
    }

    // getters e setters
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Tarefa> getListaDeTarefas() {
        return listaDeTarefas;
    }
    public void setListaDeTarefas(List<Tarefa> listaDeTarefas) {
        this.listaDeTarefas = listaDeTarefas;
    }
}