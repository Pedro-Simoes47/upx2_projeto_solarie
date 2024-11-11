package Classes;
import Connection.DatabaseConnection;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.util.List;
import java.util.Properties;
import javax.swing.*;

public class DataSearchFrame extends JFrame {
    DatabaseConnection dbConnection = new DatabaseConnection();
    public DataSearchFrame(JFrame parent) {
        super("Buscar Dados por Data");

        // Configuração do JFrame
        setSize(300, 200);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // JDatePicker
        SqlDateModel model = new SqlDateModel();
        Properties p = new Properties();
        p.put("text.today", "Hoje");
        p.put("text.month", "Mês");
        p.put("text.year", "Ano");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        // Adiciona o JDatePicker ao frame
        add(new JLabel("Selecione uma data:"));
        add(datePicker);

        // Botão para buscar dados pela data
        JButton buscarButton = new JButton("Buscar Dados");
        add(buscarButton);

        // Ação do botão buscar por data
        buscarButton.addActionListener(e -> {
            Date dataSelecionada = (Date) datePicker.getModel().getValue();

            if (dataSelecionada != null) {
                try {
                    List<EnergiaSolar> dados = dbConnection.dadosGeradosPorDia(dataSelecionada);

                    if (dados.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Nenhum dado encontrado para a data " + dataSelecionada);
                    } else {
                        StringBuilder resultado = new StringBuilder("Dados para " + dataSelecionada + ":\n");
                        for (EnergiaSolar energia : dados) {
                            resultado.append("Data: ").append(energia.getDate()).append("\n")
                                    .append("Energia Gerada: ").append(energia.getEnergiaGerada()).append("\n")
                                    .append("Panel ID: ").append(energia.getPanelID()).append("\n")
                                    .append("-----\n");
                        }
                        JOptionPane.showMessageDialog(this, resultado.toString());
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao acessar os dados: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, selecione uma data.");
            }
        });

        // Botão para voltar à tela principal
        JButton voltarButton = new JButton("Voltar");
        add(voltarButton);
        voltarButton.addActionListener(e -> {
            this.dispose();
            parent.setVisible(true); // Volta para a tela principal
        });

        setVisible(true);
    }
}