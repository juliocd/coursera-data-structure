import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

public class PhoneBook {

    private FastScanner in = new FastScanner();

    // Hashing integer values
    final int a = 32;
    final int b = 2;
    final int p = 10000019; 

    // Bookphone list
    final int m = 1000;
    HashSet[] phonebookHash =new HashSet[m];

    public static void main(String[] args) {
        new PhoneBook().processQueries();
    }

    private Query readQuery() {
        String type = in.next();
        int number = in.nextInt();
        if (type.equals("add")) {
            String name = in.next();
            return new Query(type, name, number);
        } else {
            return new Query(type, number);
        }
    }

    private void writeResponse(String response) {
        System.out.println(response);
    }

    private int hashingNumber(int value){
        final int a = 32;
        final int b = 2;

        long hash = (a * value + b) % p;
        return (int) hash % m;
    }

    private void addContact(Query query){
        // Retrieve chain
        int hashValue = hashingNumber(query.number);
        HashSet<Contact> chainContacts = phonebookHash[hashValue];
        Contact newContact = new Contact(query.name, query.number);

        chainContacts.add(newContact);
        phonebookHash[hashValue] = chainContacts;
    }

    private Contact findContact(Query query){
        // Retrieve chain
        int hashValue = hashingNumber(query.number);
        HashSet<Contact> chainContacts = phonebookHash[hashValue];

        if(!chainContacts.isEmpty()){
            for(Contact item: chainContacts){
                if(item.number == query.number){
                    return item;
                }
            }
        }

        return null;
    }

    private void removeContact(Query query){
        // Retrieve chain
        int hashValue = hashingNumber(query.number);
        HashSet<Contact> chainContacts = phonebookHash[hashValue];

        if(!chainContacts.isEmpty()){
            for(Contact item: chainContacts){  
                if(item.number == query.number){
                    chainContacts.remove(item);
                    phonebookHash[hashValue] = chainContacts;
                    break;
                }
            }
        }
    }

    private void processQuery(Query query) {
        if (query.type.equals("add")) {            
            addContact(query);
        } else if (query.type.equals("del")) {
            removeContact(query);
        } else {
            String response = "not found";
            Contact contact = findContact(query);
            if(contact != null){
                response = contact.name;
            }
            writeResponse(response);
        }
    }

    public void processQueries() {
        for(int i = 0; i < m; i++){
            phonebookHash[i] = new HashSet<Contact>();
        }

        int queryCount = in.nextInt();
        for (int i = 0; i < queryCount; ++i)
            processQuery(readQuery());
    }

    static class Contact {
        String name;
        int number;

        public Contact(String name, int number) {
            this.name = name;
            this.number = number;
        }
    }

    static class Query {
        String type;
        String name;
        int number;

        public Query(String type, String name, int number) {
            this.type = type;
            this.name = name;
            this.number = number;
        }

        public Query(String type, int number) {
            this.type = type;
            this.number = number;
        }
    }

    class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }
}
