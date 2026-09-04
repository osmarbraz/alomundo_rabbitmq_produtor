
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

/**
 * Exemplo de envio de mensagem para uma fila do RabbitMQ utilizando o serviço
 * CloudAMQP.
 */
public class Principal {

    // URL de conexão com o servidor RabbitMQ
    private static final String URL_RABBITMQ = "amqps://usuario:senha@host/virtualhost";

    // Nome da fila que receberá a mensagem
    private static final String NOME_FILA = "alo";

    // Mensagem que será enviada para a fila
    private static final String MENSAGEM = "Ola Mundo! RabbitMQ CloudAMQP";

    public static void main(String[] args) {
        try {
            // Cria a fábrica responsável por estabelecer conexões
            // com o servidor RabbitMQ.
            ConnectionFactory factory = new ConnectionFactory();

            try {
                // Configura a conexão utilizando a URL do RabbitMQ.
                factory.setUri(URL_RABBITMQ);
            } catch (URISyntaxException ex) {
                System.err.println("Erro: " + ex.getMessage());
            } catch (NoSuchAlgorithmException ex) {
                System.err.println("Erro: " + ex.getMessage());
            } catch (KeyManagementException ex) {
                System.err.println("Erro: " + ex.getMessage());
            }

            /*
             * Abre a conexão com o servidor e cria um canal
             * para realizar as operações no RabbitMQ.
             *
             * O try-with-resources garante que a conexão e o canal
             * sejam fechados automaticamente ao final da execução.
             */
            try (
                 Connection connection = factory.newConnection();  Channel channel = connection.createChannel()) {

                // Define as características da fila.
                boolean durable = true;
                boolean exclusive = false;
                boolean autoDelete = false;

                /*
                 * Declara a fila no RabbitMQ.
                 *
                 * durable = true:
                 * A fila será mantida mesmo após uma reinicialização
                 * do servidor RabbitMQ.
                 *
                 * exclusive = false:
                 * A fila não pertence exclusivamente à conexão atual.
                 *
                 * autoDelete = false:
                 * A fila não será excluída automaticamente.
                 */
                channel.queueDeclare(NOME_FILA, durable, exclusive, autoDelete, null);

                // Converte a mensagem para um array de bytes utilizando UTF-8.
                byte[] corpoMensagem = MENSAGEM.getBytes(StandardCharsets.UTF_8);

                /*
                 * Publica a mensagem na fila.
                 *
                 * "" representa a exchange padrão do RabbitMQ.
                 * NOME_FILA identifica a fila de destino.
                 * PERSISTENT_TEXT_PLAIN indica que a mensagem é
                 * persistente e possui conteúdo textual.
                 */
                channel.basicPublish("", NOME_FILA, MessageProperties.PERSISTENT_TEXT_PLAIN, corpoMensagem);

                // Exibe uma confirmação no console.
                System.out.println("Mensagem enviada: '" + MENSAGEM + "'");
            }

        } catch (IOException | TimeoutException | RuntimeException e) {

            // Exibe uma mensagem de erro caso ocorra algum problema.
            System.err.println("Erro ao enviar a mensagem: " + e.getMessage());
        }
    }
}
