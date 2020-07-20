package katas.phoneDir;

/*
John keeps a backup of his old personal phone book as a text file. On each line of the file he can find the phone number (formated as +X-abc-def-ghij where X stands for one or
two digits), the corresponding name between < and > and the address.

Unfortunately everything is mixed, things are not always in the same order; parts of lines are cluttered with non-alpha-numeric characters (except inside phone number and name).

Examples of John's phone book lines:

"/+1-541-754-3010 156 Alphand_St. <J Steeve>\n"

" 133, Green, Rd. <E Kustur> NY-56423 ;+1-541-914-3010!\n"

"<Anastasia> +48-421-674-8974 Via Quirinal Roma\n"

Could you help John with a program that, given the lines of his phone book and a phone number num returns a string for this number : "Phone => num, Name => name, Address => adress"

Examples:

s = "/+1-541-754-3010 156 Alphand_St. <J Steeve>\n
133, Green, Rd. <E Kustur> NY-56423 ;+1-541-914-3010!\n"

phone(s, "1-541-754-3010") should return "Phone => 1-541-754-3010, Name => J Steeve, Address => 156 Alphand St."
It can happen that there are many people for a phone number num, then

return : "Error => Too many people: num"

or it can happen that the number num is not in the phone book, in that case

return: "Error => Not found: num"

You can see other examples in the test cases.

JavaScript random tests completed by @matt c

Note
Codewars stdout doesn't print part of a string when between < and >
 */


public class PhoneDir {

    public static void main(String[] args) {
        //String s = "/+1-541-754-3010 156 Alphand_St. <J Steeve>\n 133, Green, Rd. <E Kustur> NY-56423 ;+1-541-914-3010!\n";
        String s = "/+1-541-754-3010 156 Alphand_St. <J Steeve>\n 133, Green, Rd. <E Kustur> NY-56423 ;+1-541-914-3010\n"
                + "+1-541-984-3012 <P Reed> /PO Box 530; Pollocksville, NC-28573\n :+1-321-512-2222 <Paul Dive> Sequoia Alley PQ-67209\n"
                + "+1-741-984-3090 <Peter Reedgrave> _Chicago\n :+1-921-333-2222 <Anna Stevens> Haramburu_Street AA-67209\n"
                + "+1-111-544-8973 <Peter Pan> LA\n +1-921-512-2222 <Wilfrid Stevens> Wild Street AA-67209\n"
                + "<Peter Gone> LA ?+1-121-544-8974 \n <R Steell> Quora Street AB-47209 +1-481-512-2222\n"
                + "<Arthur Clarke> San Antonio $+1-121-504-8974 TT-45120\n <Ray Chandler> Teliman Pk. !+1-681-512-2222! AB-47209,\n"
                + "<Sophia Loren> +1-421-674-8974 Bern TP-46017\n <Peter O'Brien> High Street +1-908-512-2222; CC-47209\n"
                + "<Anastasia> +48-421-674-8974 Via Quirinal Roma\n <P Salinger> Main Street, +1-098-512-2222, Denver\n"
                + "<C Powel> *+19-421-674-8974 Chateau des Fosses Strasbourg F-68000\n <Bernard Deltheil> +1-498-512-2222; Mount Av.  Eldorado\n"
                + "+1-099-500-8000 <Peter Crush> Labrador Bd.\n +1-931-512-4855 <William Saurin> Bison Street CQ-23071\n"
                + "<P Salinge> Main Street, +1-098-512-2222, Denve\n"+ "<P Salinge> Main Street, +1-098-512-2222, Denve\n";
        System.out.println(phone(s, "48-421-674-8974"));
    }

    public static String phone(String string, String num) {
        Line line = getLine(string, num, 0);

        if (line == null) {
            return "Error => Not found: " + num;
        } else if (getLine(string, num, line.end) != null) {
            return "Error => Too many people: " + num;
        } else {
            Record record = parse(string, line, num);

            return "Phone => " + num + ", Name => " + record.name + ", Address => " + record.address;
        }
    }

    private static Record parse(String string, Line line, String num) {
        StringBuilder name = new StringBuilder();
        StringBuilder address = new StringBuilder();

        for (int i = line.start; i < line.end; i++) {
            char atIdx = string.charAt(i);
            if (atIdx == '_')
                atIdx = ' ';

            if (atIdx == '<') {
                i++;
                while (string.charAt(i) != '>') {
                    name.append(string.charAt(i));
                    i++;
                }
            } else if (atIdx == '+') {
                i = i + num.length();

            } else if (Character.isAlphabetic(atIdx) || Character.isDigit(atIdx) || atIdx == '.' || atIdx == '-') {
                address.append(atIdx);

            } else if (atIdx == ' ') {
                if ((address.length() != 0) && address.charAt(address.length() - 1) != ' ') {
                    address.append(atIdx);
                }
            }
        }

        if (address.length() > 0 && address.charAt(address.length() - 1) == ' ')
            address.deleteCharAt(address.length() - 1);

        return new Record(name.toString(), address.toString());
    }

//    private static Line getLine(String string, String num, int i) {
//        if (string.length() == i)
//            return null;
//
//        int endChar = 0;
//        for (int j = i; j < string.length(); j++) {
//            if (string.charAt(j) == '\n') {
//                endChar = j;
//                break;
//            }
//        }
//        String line = string.substring(i, endChar);
//
//        if (line.contains("+" + num))
//            return new Line(i, endChar);
//        else
//            return getLine(string, num, endChar + 1);
//    }

    private static Line getLine(String string, String num, int i) {
        int idx = string.indexOf("+" + num, i);
        if (idx < 0)
            return null;

        int start = idx;
        while ((start > 0) && (string.charAt(start) != '\n'))
            start--;

        int end = idx;
        while ((end < string.length()) && (string.charAt(end) != '\n'))
            end++;

        return new Line(start, end);
    }

    private static class Line {
        private int start;
        private int end;

        public Line(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public String toString() {
            return "Line{" +
                    "start=" + start +
                    ", end=" + end +
                    '}';
        }
    }

    private static class Record {
        private String name;
        private String address;

        public Record(String name, String address) {
            this.name = name;
            this.address = address;
        }
    }
}
