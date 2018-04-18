import config.ConfigurationManager;
import model.prank.Prank;
import model.prank.PrankGenerator;
import smtp.SmtpClient;

import java.io.IOException;
import java.util.List;

import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) {
        ConfigurationManager configurationManager = null;

        try {
            configurationManager = new ConfigurationManager();
        } catch (IOException e) {
            e.printStackTrace();
            exit(1);
        }

        SmtpClient smtpClient = new SmtpClient(configurationManager.getSmtpServerAddress(), configurationManager.getSmtpServerPort());

        PrankGenerator prankGenerator = new PrankGenerator(configurationManager);

        List<Prank> pranks = prankGenerator.generatePranks();

        for (Prank prank : pranks) {
            try {
                smtpClient.sendMessage(prank.generateMessage());
            } catch (IOException e) {
                e.printStackTrace();
                exit(1);
            }
        }
    }
}