package com.dsa.HomeLibrarySystem.controller;

import com.dsa.HomeLibrarySystem.model.*;
import com.dsa.HomeLibrarySystem.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {
    private final MemberService memberService;
    private final BookService bookService;
    private final JournalService journalService;
    private final AuthorService authorService;
    private final BookshelfLocationService bookshelfLocationService;
    private final LoanService loanService;

    public HomeController(MemberService memberService, BookService bookService, JournalService journalService,
                          AuthorService authorService, BookshelfLocationService bookshelfLocationService, LoanService loanService) {
        this.memberService = memberService;
        this.bookService = bookService;
        this.journalService = journalService;
        this.authorService = authorService;
        this.bookshelfLocationService = bookshelfLocationService;
        this.loanService = loanService;
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

    @GetMapping("/members")
    public String members(Model model) {
        List<Member> memberList = memberService.getAllMembers();
        model.addAttribute("members", memberList);
        return "members";
    }

    @GetMapping("/add-loan")
    public String addLoanForm() {
        return "add-loan";
    }

    @PostMapping("/add-loan")
    public String addLoan(@RequestParam("memberId") Long memberId,
                          @RequestParam("bookId") Long bookId,
                          @RequestParam("loanDate") Date loanDate,
                          @RequestParam("returnDate") Date returnDate,
                          Model model) {
        try {
            // Validate member and book existence
            Optional<Member> member = memberService.getMemberById(memberId);
            Optional<Book> book = bookService.getBookById(bookId);

            if (member.isEmpty() || book.isEmpty()) {
                model.addAttribute("message", "Invalid member or book!");
                return "add-loan"; // Redirect back to the form
            }

            // Create a new Loan object
            Loan newLoan = new Loan();
            newLoan.setBorrower(member.get());
            newLoan.setItem(book.get());
            newLoan.setLoanDate(loanDate);
            newLoan.setReturnDate(returnDate);

            // Save the loan
            loanService.saveOrUpdateLoan(newLoan);
            model.addAttribute("message", "Loan added successfully!");

            return "redirect:/loans";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "An error occurred while adding the loan: " + e.getMessage());
            return "add-loan";
        }
    }

    @GetMapping("/loans")
    public String loans(Model model) {
        List<Loan> loanList = loanService.getAllLoans();
        model.addAttribute("loans", loanList);
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
                          @RequestParam("section") String section,
                          @RequestParam("shelfNumber") Long shelfNumber,
                          @RequestParam("position") String position,
                          @RequestParam("availableCopies") int availableCopies,
                          Model model) {
        try {
            Optional<BookshelfLocation> location = bookshelfLocationService.getBookshelfLocation(section, shelfNumber, position);
            if (location.isEmpty()) {
                model.addAttribute("message", "Invalid bookshelf location!");
                return "add-book";
            }

            Book newBook = new Book();
            newBook.setTitle(title);

            Optional<Author> optionalAuthor = authorService.getAuthorById(authorId);
            if (optionalAuthor.isPresent()) {
                newBook.setAuthors(List.of(optionalAuthor.get()));
            } else {
                model.addAttribute("message", "Author not found!");
                return "add-book";
            }

            newBook.setISBN(isbn);
            newBook.setLocation(location.orElse(null));
            newBook.setTotalCopies(totalCopies);
            newBook.setAvailableCopies(availableCopies);

            bookService.saveOrUpdateBook(newBook);
            model.addAttribute("message", "Book added successfully!");

            return "redirect:/books";
        } catch (Exception e) {
            e.printStackTrace(); // Log the error
            model.addAttribute("message", "An error occurred while adding the book: " + e.getMessage());
            return "add-book";
        }
    }

    @GetMapping("/books")
    public String books(Model model) {
        List<Book> bookList = bookService.getAllBooks();
        model.addAttribute("books", bookList);
        return "books";
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
                             @RequestParam("section") String section,
                             @RequestParam("shelfNumber") Long shelfNumber,
                             @RequestParam("position") String position,
                             @RequestParam("availableCopies") int availableCopies,
                             Model model) {
        try {
            Optional<BookshelfLocation> location = bookshelfLocationService.getBookshelfLocation(section, shelfNumber, position);
            if (location.isEmpty()) {
                model.addAttribute("message", "Invalid bookshelf location!");
                return "add-journal";
            }

            Journal newJournal = new Journal();
            newJournal.setTitle(title);

            Optional<Author> optionalAuthor = authorService.getAuthorById(authorId);
            if (optionalAuthor.isPresent()) {
                newJournal.setAuthors(List.of(optionalAuthor.get()));
            } else {
                model.addAttribute("message", "Author not found!");
                return "add-journal";
            }

            newJournal.setIssue(journalIssue);
            newJournal.setVolume(volume);
            newJournal.setLocation(location.orElse(null));
            newJournal.setAvailableCopies(availableCopies);

            journalService.saveOrUpdateJournal(newJournal);
            model.addAttribute("message", "Journal added successfully!");

            return "redirect:/journals"; // Redirect to journal details page
        } catch (Exception e) {
            e.printStackTrace(); // Log the error
            model.addAttribute("message", "An error occurred while adding the journal: " + e.getMessage());
            return "add-journal";
        }
    }

    @GetMapping("/journals")
    public String journals(Model model) {
        List<Journal> journalList = journalService.getAllJournals();
        model.addAttribute("journals", journalList);
        return "journals";
    }

}
