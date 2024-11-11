import Classes.DateLabelFormatter;
import Connection.DatabaseConnection;
import Classes.EnergiaSolar;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;
import Classes.DataSearchFrame;
import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.util.List;
import java.util.Properties;


public class Main {
    public static void main(String[] args) {
        DatabaseConnection dbConnection = new DatabaseConnection();

        try {

            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Solarie Home Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 200);
        frame.setLayout(new BorderLayout());

        JPanel messagePanel = new JPanel();
        JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>Este aplicativo permite monitorar e visualizar dados de geração de energia solar.<br>Escolha uma opção abaixo para visualizar todos os dados ou buscar por data específica.</div></html>");
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER); // Centraliza o texto horizontalmente
        messagePanel.add(messageLabel);


        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));


        JButton buscarTodosButton = new JButton("Buscar todos os Dados");


        buscarTodosButton.addActionListener(e -> {
            try {
                List<EnergiaSolar> dados = dbConnection.dadosGerados();
                if(dados.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Nenhum dado encontrado!");
                }else {
                    StringBuilder resultado = new StringBuilder("Todos os dados:\n");
                    for (EnergiaSolar energia : dados) {
                        resultado.append("Data: ").append(energia.getDate()).append("\n")
                                .append("Energia Gerada: ").append(energia.getEnergiaGerada()).append("\n")
                                .append("Panel ID: ").append(energia.getPanelID()).append("\n")
                                .append("-----\n");
                    }
                    JTextArea textArea = new JTextArea();
                    textArea.setText(resultado.toString());
                    textArea.setEditable(false);


                    JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setPreferredSize(new Dimension(250, 150));
                    textArea.setCaretPosition(0);

                    JOptionPane.showMessageDialog(frame, scrollPane, "Todos os Dados", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                System.out.println("Erro ao acessar os dados: " + ex.getMessage());
            }
        });
        JButton buscarPorDataButton = new JButton("Buscar Dados por Data");


        buscarPorDataButton.addActionListener(e -> {

            new DataSearchFrame(frame);
        });
        buttonPanel.add(buscarTodosButton);
        buttonPanel.add(buscarPorDataButton);
        frame.add(messagePanel,BorderLayout.NORTH);
        frame.add(buttonPanel,BorderLayout.SOUTH);

        frame.setVisible(true);

    }
}

