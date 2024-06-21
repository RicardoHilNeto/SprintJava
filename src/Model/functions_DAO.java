package Model;

import javax.swing.JOptionPane;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class functions_DAO {

    public static void surgeonRegister() {
        String surgeonName = JOptionPane.showInputDialog(null, "Write your name:");
        String surgeonSpecialty = JOptionPane.showInputDialog(null, "Write your specialty:"
                + "\n1 - General\n2 - Cardiovascular\n3 - Neurosurgery\n4 - Digestive System\n5 - Head and Neck\n6 - Plastic Surgery\n7 - Vascular \n8 - Urology\n9 - Other");

        int intSurgeonSpecialty;
        try {
            intSurgeonSpecialty = Integer.parseInt(surgeonSpecialty);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input for specialty. Please enter a number between 1 and 9.");
            return;
        }

        switch (intSurgeonSpecialty) {
            case 1:
                surgeonSpecialty = "General";
                break;
            case 2:
                surgeonSpecialty = "Cardiovascular";
                break;
            case 3:
                surgeonSpecialty = "Neurosurgery";
                break;
            case 4:
                surgeonSpecialty = "Digestive System";
                break;
            case 5:
                surgeonSpecialty = "Head and Neck";
                break;
            case 6:
                surgeonSpecialty = "Plastic Surgery";
                break;
            case 7:
                surgeonSpecialty = "Vascular";
                break;
            case 8:
                surgeonSpecialty = "Urology";
                break;
            case 9:
                surgeonSpecialty = JOptionPane.showInputDialog(null, "Write your specialty:");
                break;
            default:
                JOptionPane.showMessageDialog(null, "Invalid choice. Please enter a number between 1 and 9.");
                return;
        }

        storeUser(surgeonName, surgeonSpecialty);
        JOptionPane.showMessageDialog(null, "Name: " + surgeonName + " Specialty: " + surgeonSpecialty);
    }

    public static ArrayList<User> users = new ArrayList<>();

    public static class User {
        private String name;
        private String specialty;
        private List<Procedure> procedures = new ArrayList<>();

        public User(String name, String specialty) {
            this.name = name;
            this.specialty = specialty;
        }

        public String getName() {
            return name;
        }

        public String getSpecialty() {
            return specialty;
        }

        public void addProcedure(Procedure procedure) {
            procedures.add(procedure);
        }

        public List<Procedure> getProcedures() {
            return procedures;
        }
    }

    public static class Procedure {
        private int type;
        private int timeInSeconds;
        private int sushiCount;
        private Date date;

        public Procedure(int type, int timeInSeconds, int sushiCount, Date date) {
            this.type = type;
            this.timeInSeconds = timeInSeconds;
            this.sushiCount = sushiCount;
            this.date = date;
        }

        public int getType() {
            return type;
        }

        public int getTimeInSeconds() {
            return timeInSeconds;
        }

        public int getSushiCount() {
            return sushiCount;
        }

        public Date getDate() {
            return date;
        }
    }

    public static void storeUser(String name, String specialty) {
        if (name.isEmpty() || specialty.isEmpty()) {
            System.out.println("Please fill in all fields.");
            return;
        }

        users.add(new User(name, specialty));
    }

    public static String validateUser(String nameInput) {
        for (User user : users) {
            if (user.getName().equals(nameInput)) {
                return user.getName();
            }
        }

        return null;
    }

    public static User findUserByName(String nameInput) {
        for (User user : users) {
            if (user.getName().equals(nameInput)) {
                return user;
            }
        }

        return null;
    }

    public static void registerProcedure() {
        String surgeonName = JOptionPane.showInputDialog(null, "Write the surgeon's name:");
        User user = findUserByName(surgeonName);
        if (user == null) {
            JOptionPane.showMessageDialog(null, "Surgeon not found.");
            return;
        }

        String procedureTypeStr = JOptionPane.showInputDialog(null, "Write your procedure type (1 or 2):");
        int procedureType;
        try {
            procedureType = Integer.parseInt(procedureTypeStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input for procedure type. Please enter a valid number.");
            return;
        }

        int timeInSeconds;
        if (procedureType == 1) {
            String timeStr = JOptionPane.showInputDialog(null, "Enter your time like the example below\n(0:20) (1:12):");
            timeInSeconds = parseTimeToSeconds(timeStr);
            if (timeInSeconds < 0) {
                JOptionPane.showMessageDialog(null, "Invalid time format.");
                return;
            }
        } else if (procedureType == 2) {
            timeInSeconds = 80; // Tempo predefinido de 1:20 para procedimentos do tipo 2
        } else {
            JOptionPane.showMessageDialog(null, "Invalid procedure type. Please enter 1 or 2.");
            return;
        }

        String sushiCountStr = JOptionPane.showInputDialog(null, "Enter the number of sushis rolled:");
        int sushiCount;
        try {
            sushiCount = Integer.parseInt(sushiCountStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input for sushi count. Please enter a valid number.");
            return;
        }

        String dateStr = JOptionPane.showInputDialog(null, "Enter the date of the procedure (yyyy-MM-dd):");
        Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Invalid date format. Please enter a date in the format yyyy-MM-dd.");
            return;
        }

        user.addProcedure(new Procedure(procedureType, timeInSeconds, sushiCount, date));
    }

    private static int parseTimeToSeconds(String timeStr) {
        try {
            String[] parts = timeStr.split(":");
            int minutes = Integer.parseInt(parts[0]);
            int seconds = Integer.parseInt(parts[1]);
            return minutes * 60 + seconds;
        } catch (Exception e) {
            return -1;
        }
    }

    public static void generateRankings() {
        List<Procedure> procedure1List = new ArrayList<>();
        List<Procedure> procedure2List = new ArrayList<>();

        for (User user : users) {
            for (Procedure procedure : user.getProcedures()) {
                if (procedure.getType() == 1) {
                    procedure1List.add(procedure);
                } else if (procedure.getType() == 2) {
                    procedure2List.add(procedure);
                }
            }
        }

        List<Procedure> sortedProcedure1List = procedure1List.stream()
                .sorted(Comparator.comparingInt(Procedure::getTimeInSeconds))
                .collect(Collectors.toList());

        List<Procedure> sortedProcedure2List = procedure2List.stream()
                .sorted(Comparator.comparingInt(Procedure::getSushiCount).reversed())
                .collect(Collectors.toList());

        StringBuilder ranking1 = new StringBuilder("Ranking for time:\n");
        for (Procedure p : sortedProcedure1List) {
            User user = findUserByProcedure(p);
            ranking1.append(user.getName()).append(" - ")
                    .append(formatTime(p.getTimeInSeconds())).append(" - ")
                    .append("Sushis rolled: ").append(p.getSushiCount()).append(" - ")
                    .append("Date: ").append(new SimpleDateFormat("yyyy-MM-dd").format(p.getDate())).append("\n");
        }

        StringBuilder ranking2 = new StringBuilder("Ranking for sushis:\n");
        for (Procedure p : sortedProcedure2List) {
            User user = findUserByProcedure(p);
            ranking2.append(user.getName()).append(" - ")
                    .append("1:20").append(" - ")
                    .append("Sushis rolled: ").append(p.getSushiCount()).append(" - ")
                    .append("Date: ").append(new SimpleDateFormat("yyyy-MM-dd").format(p.getDate())).append("\n");
        }

        JOptionPane.showMessageDialog(null, ranking1.toString());
        JOptionPane.showMessageDialog(null, ranking2.toString());
    }

    private static User findUserByProcedure(Procedure procedure) {
        for (User user : users) {
            if (user.getProcedures().contains(procedure)) {
                return user;
            }
        }
        return null;
    }

    private static String formatTime(int timeInSeconds) {
        int minutes = timeInSeconds / 60;
        int seconds = timeInSeconds % 60;
        return String.format("%d:%02d", minutes, seconds);
    }
}
