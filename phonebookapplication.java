
package phonebookapplication;
import java.util.*;

class Contact {
    private String name;
    private String phoneNumber;
    private Date addDate;

    public Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.addDate = new Date();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getAddDate() {
        return addDate;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Phone: " + phoneNumber + ", Added: " + addDate;
    }
}

public class PhonebookApplication {
    private static List<Contact> contacts = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nPhonebook Menu:");
            System.out.println("1. View Contacts");
            System.out.println("2. Add Contact");
            System.out.println("3. Update Contact");
            System.out.println("4. Delete Contact");
            System.out.println("5. Search Contact (By Name)");
            System.out.println("6. Sort Contacts By Add Date");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewContacts();
                    break;
                    
                case 2:
                    addContact(scanner);
                    break;
                case 3:
                    updateContact(scanner);
                    break;
                case 4:
                    deleteContact(scanner);
                    break;
                case 5:
                    searchContact(scanner);
                    break;
                case 6:
                    sortContactsByDateDescending();
                    break;
                case 7:
                    System.out.println("Exiting application. Goodbye!");
                    return;
                default:
                    System.out.println("====Invalid choice. Please try again.====");
            }
        }
    }

    private static void addContact(Scanner scanner) {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();
        contacts.add(new Contact(name, phoneNumber));
        System.out.println("====Contact added successfully.====");
    }

    private static void updateContact(Scanner scanner) {
        System.out.print("Enter name of the contact to update: ");
        String name = scanner.nextLine();
        Contact contact = findContactByName(name);
        if (contact != null) {
            System.out.print("Enter new name: ");
            String newName = scanner.nextLine();
            System.out.print("Enter new phone number: ");
            String newPhoneNumber = scanner.nextLine();
            contact.setName(newName);
            contact.setPhoneNumber(newPhoneNumber);
            System.out.println("====Contact updated successfully.====");
        } else {
            System.out.println("====Contact not found.====");
        }
    }

    private static void deleteContact(Scanner scanner) {
        System.out.print("Enter name of the contact to delete: ");
        String name = scanner.nextLine();
        Contact contact = findContactByName(name);
        if (contact != null) {
            contacts.remove(contact);
            System.out.println("Contact deleted successfully.");
        } else {
            System.out.println("Contact not found.");
        }
    }

    private static void searchContact(Scanner scanner) {
        System.out.print("Enter name to search: ");
        String name = scanner.nextLine();
        List<Contact> sortedContacts = new ArrayList<>(contacts);
        sortedContacts.sort(Comparator.comparing(Contact::getName));
        int index = binarySearch(sortedContacts, name);
        if (index != -1) {
            System.out.println("Contact found: " + sortedContacts.get(index));
        } else {
            System.out.println("Contact not found.");
        }
    }

    private static int binarySearch(List<Contact> sortedContacts, String name) {
        int low = 0;
        int high = sortedContacts.size() - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            Contact midContact = sortedContacts.get(mid);
            int comparison = midContact.getName().compareToIgnoreCase(name);
            if (comparison == 0) {
                return mid;
            } else if (comparison < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    private static void sortContactsByDateDescending() {
        List<Contact> sortedContacts = new ArrayList<>(contacts);
        mergeSortDescending(sortedContacts, 0, sortedContacts.size() - 1);
        System.out.println("====Contacts sorted by add date (Descending):====");
        for (Contact contact : sortedContacts) {
            System.out.println(contact);
        }
    }

    private static void mergeSortDescending(List<Contact> contacts, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSortDescending(contacts, left, mid);
            mergeSortDescending(contacts, mid + 1, right);
            mergeDescending(contacts, left, mid, right);
        }
    }

    private static void mergeDescending(List<Contact> contacts, int left, int mid, int right) {
        List<Contact> leftList = new ArrayList<>(contacts.subList(left, mid + 1));
        List<Contact> rightList = new ArrayList<>(contacts.subList(mid + 1, right + 1));

        int i = 0, j = 0, k = left;
        while (i < leftList.size() && j < rightList.size()) {
            if (leftList.get(i).getAddDate().compareTo(rightList.get(j).getAddDate()) >= 0) {
                contacts.set(k++, leftList.get(i++));
            } else {
                contacts.set(k++, rightList.get(j++));
            }
        }

        while (i < leftList.size()) {
            contacts.set(k++, leftList.get(i++));
        }

        while (j < rightList.size()) {
            contacts.set(k++, rightList.get(j++));
        }
    }

    private static void viewContacts() {
        if (contacts.isEmpty()) {
            System.out.println("====No contacts available.====");
        } else {
            System.out.println("====List of contacts:====");
            for (Contact contact : contacts) {
                System.out.println(contact);
            }
        }
    }

    private static Contact findContactByName(String name) {
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(name)) {
                return contact;
            }
        }
        return null;
    }
}
