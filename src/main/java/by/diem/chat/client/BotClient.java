package by.diem.chat.client;


import by.diem.chat.ConsoleHelper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BotClient extends Client {


    private static int botsCounter = 0;



    public static void main(String[] args) {
        new BotClient().run();
    }


    /** inner class */
    public class BotSocketThread extends SocketThread {


        @Override
        protected void clientMainLoop() throws IOException, ClassNotFoundException {


            sendTextMessage("Привет чатику. Я бот. Понимаю команды: дата, день, месяц, год, время, час, минуты, секунды.");


            super.clientMainLoop();

        }


        @Override
        protected void processIncomingMessage(String message) {

            // Вывести в консоль текст полученного сообщения message
            ConsoleHelper.writeMessage(message);

            // Получить из message имя отправителя и текст сообщения. Они разделены ": "
            String senderName = "";
            String senderMessageText;

            if (message.contains(": ")) {
                senderName = message.substring(0, message.indexOf(": "));
                senderMessageText = message.substring(message.indexOf(": ") + 2);
            }
            else {
                senderMessageText = message;
            }


            SimpleDateFormat format = null;

            if ("дата".equalsIgnoreCase(senderMessageText)) {
                format = new SimpleDateFormat("d.MM.yyyy");
            }
            else if ("день".equalsIgnoreCase(senderMessageText)) {
                format = new SimpleDateFormat("d");
            }
            else if ("месяц".equalsIgnoreCase(senderMessageText)) {
                format = new SimpleDateFormat("MMMM");
            }
            else if ("год".equalsIgnoreCase(senderMessageText)) {
                format = new SimpleDateFormat("yyyy");
            }
            else if ("время".equalsIgnoreCase(senderMessageText)) {
                format = new SimpleDateFormat("H:mm:ss");
            }
            else if ("час".equalsIgnoreCase(senderMessageText)) {
                format = new SimpleDateFormat("H");
            }
            else if ("минуты".equalsIgnoreCase(senderMessageText)) {
                format = new SimpleDateFormat("m");
            }
            else if ("секунды".equalsIgnoreCase(senderMessageText)) {
                format = new SimpleDateFormat("s");
            }

            if (format != null)
            {
                sendTextMessage("Информация для " + senderName + ": " + format.format(Calendar.getInstance().getTime()));
            }

        }

    }



    @Override
    protected SocketThread getSocketThread() {
        //Он должен создавать и возвращать объект класса BotSocketThread
        return new BotSocketThread();
    }

    @Override
    protected boolean shouldSentTextFromConsole() {
        // бот не должен отправлять текст введенный в консоль.
        return false;
    }

    @Override
    protected String getUserName() {
        // У ботов должны быть разные имена
        if (botsCounter == 99) {
            botsCounter = 0;
        }

        return "date_bot_" + botsCounter++;

    }
}
