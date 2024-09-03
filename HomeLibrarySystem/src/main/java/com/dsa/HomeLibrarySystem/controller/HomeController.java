package com.dsa.HomeLibrarySystem.controller;

import com.dsa.HomeLibrarySystem.model.*;
import com.dsa.HomeLibrarySystem.service.AuthorService;
import com.dsa.HomeLibrarySystem.service.BookService;
import com.dsa.HomeLibrarySystem.service.JournalService;
import com.dsa.HomeLibrarySystem.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    private final MemberService memberService;

    private final BookService bookService;

    private final JournalService journalService;

    private final AuthorService authorService;

    public HomeController(MemberService memberService, BookService bookService, JournalService journalService, AuthorService authorService) {
        this.memberService = memberService;
        this.bookService = bookService;
        this.journalService = journalService;
        this.authorService = authorService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/add-member")
    public String showAddMemberForm() {
        return "add-member";
    }

    @PostMapping("/add-member")
    public String addMember(@RequestParam("name") String name,
                            @RequestParam("email") String email,
                            @RequestParam("password") String password,
                            Model model) {
        Member newMember = new Member();
        newMember.setName(name);
        newMember.setEmail(email);
        newMember.setPassword(password);
        newMember.setRole("USER");

        memberService.saveOrUpdateMember(newMember);

        model.addAttribute("message", "Member added successfully!");
        return "redirect:/members";
    }

    @GetMapping("/add-loan")
    public String addLoan() {
        return "add-loan";
    }

    @GetMapping("/members")
    public String members() {
        return "members";
    }

    @GetMapping("/loans")
    public String loans() {
        return "loans";
    }

    @GetMapping("/add-book")
    public String showAddBookForm() {
        return "add-book";
    }

    @PostMapping("/add-book")
    public String addBook(@RequestParam("title") String title,
                          @RequestParam("authorId") Long authorId,
                          @RequestParam("isbn") Long isbn,
                          @RequestParam("totalCopies") int totalCopies,
                          @RequestParam("location") BookshelfLocation location,
                          @RequestParam("availableCopies") int availableCopies,
                          Model model) {
        Book newBook = new Book();
        newBook.setTitle(title);

        // Fetch Author by ID
        Optional<Author> optionalAuthor = authorService.getAuthorById(authorId);
        if (optionalAuthor.isPresent()) {
            newBook.setAuthors(List.of(optionalAuthor.get())); // Add the author to the list
        } else {
            newBook.setAuthors(List.of()); // Handle case where author is not found
            model.addAttribute("message", "Author not found!");
            return "add-book"; // Return to the add-book page with an error message
        }

        newBook.setISBN(isbn);
        newBook.setLocation(location);
        newBook.setTotalCopies(totalCopies);
        newBook.setAvailableCopies(availableCopies);

        bookService.saveOrUpdateBook(newBook);

        model.addAttribute("message", "Book added successfully!");
        return "redirect:/dashboard";
    }

    @GetMapping("/add-journal")
    public String showAddJournalForm() {
        return "add-journal";
    }

    @PostMapping("/add-journal")
    public String addJournal(@RequestParam("title") String title,
                             @RequestParam("authorId") Long authorId,
                             @RequestParam("journalIssue") int journalIssue,
                             @RequestParam("volume") int volume,
                             @RequestParam("location") BookshelfLocation location,
                             @RequestParam("availableCopies") int availableCopies,
                             Model model) {
        Journal newJournal = new Journal();
        newJournal.setTitle(title);

        // Fetch Author by ID
        Optional<Author> optionalAuthor = authorService.getAuthorById(authorId);
        if (optionalAuthor.isPresent()) {
            newJournal.setAuthors(List.of(optionalAuthor.get())); // Add the author to the list
        } else {
            newJournal.setAuthors(List.of()); // Handle case where author is not found
            model.addAttribute("message", "Author not found!");
            return "add-journal"; // Return to the add-journal page with an error message
        }

        newJournal.setIssue(journalIssue);
        newJournal.setVolume(volume);
        newJournal.setLocation(location);
        newJournal.setAvailableCopies(availableCopies);

        journalService.saveOrUpdateJournal(newJournal);

        model.addAttribute("message", "Journal added successfully!");
        return "redirect:/dashboard";
    }

    @GetMapping("/book-details")
    public String bookDetails(@RequestParam("id") Long id, Model model) {
        Optional<Book> book = bookService.getBookById(id);
        if (book.isPresent()) {
            model.addAttribute("book", book.get());
            return "book-details"; // This should be the name of your book details HTML template
        } else {
            return "redirect:/error"; // Create an error page if book not found
        }
    }

    @GetMapping("/journal-details")
    public String journalDetails(@RequestParam("id") Long id, Model model) {
        Optional<Journal> journal = journalService.getJournalById(id);
        if (journal.isPresent()) {
            model.addAttribute("journal", journal.get());
            return "journal-details"; // This should be the name of your journal details HTML template
        } else {
            return "redirect:/error"; // Create an error page if journal not found
        }
    }

}
