package com.dsa.HomeLibrarySystem.controller;

import com.dsa.HomeLibrarySystem.model.*;
import com.dsa.HomeLibrarySystem.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {
    private final MemberService memberService;
    private final BookService bookService;
    private final JournalService journalService;
    private final AuthorService authorService;
    private final BibliographicArtifactService artifactService;
    private final BookshelfLocationService bookshelfLocationService;
    private final LoanService loanService;
    private final ReviewService reviewService;

    public HomeController(MemberService memberService, BookService bookService, JournalService journalService,
                          AuthorService authorService, BibliographicArtifactService artifactService, BookshelfLocationService bookshelfLocationService,
                          LoanService loanService, ReviewService reviewService) {
        this.memberService = memberService;
        this.bookService = bookService;
        this.journalService = journalService;
        this.authorService = authorService;
        this.artifactService = artifactService;
        this.bookshelfLocationService = bookshelfLocationService;
        this.loanService = loanService;
        this.reviewService = reviewService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<BibliographicArtifact> artifacts = artifactService.getAllBibliographicArtifacts();
        model.addAttribute("books", artifacts);
        model.addAttribute("journals", artifacts);
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
    public String showAddLoanForm(Model model) {
        List<Member> members = memberService.getAllMembers();
        List<BibliographicArtifact> artifacts = artifactService.getAllBibliographicArtifacts();
        model.addAttribute("members", members);
        model.addAttribute("artifacts", artifacts);
        return "add-loan";
    }

    @PostMapping("/add-loan")
    public String addLoan(@RequestParam("memberId") Long memberId,
                          @RequestParam("itemId") Long itemId,
                          @RequestParam("itemType") String itemType,
                          Model model) {
        try {
            Member member = memberService.getMemberById(memberId).orElseThrow(() -> new IllegalArgumentException("Member not found"));
            BibliographicArtifact item;

            if ("book".equals(itemType)) {
                item = bookService.getBookById(itemId).orElseThrow(() -> new IllegalArgumentException("Book not found"));
            } else if ("journal".equals(itemType)) {
                item = journalService.getJournalById(itemId).orElseThrow(() -> new IllegalArgumentException("Journal not found"));
            } else {
                throw new IllegalArgumentException("Invalid item type");
            }
            Loan loan = member.borrowItem(item);
            if (loan != null) {
                loanService.saveOrUpdateLoan(loan);
                model.addAttribute("message", "Loan created successfully!");
            } else {
                model.addAttribute("message", "Item is not available for borrowing.");
            }
            return "redirect:/loans"; // Redirect to the loans page
        } catch (Exception e) {
            model.addAttribute("message", "An error occurred while creating the loan: " + e.getMessage());
            return "add-loan"; // Return to the form with an error message
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
                          @RequestParam("isbn") String isbn,
                          @RequestParam("totalCopies") int totalCopies,
                          @RequestParam("section") String section,
                          @RequestParam("shelfNumber") Long shelfNumber,
                          @RequestParam("position") String position,
                          @RequestParam("availableCopies") int availableCopies,
                          Model model) {

            Optional<BookshelfLocation> location = bookshelfLocationService.getBookshelfLocation(section, shelfNumber, position);
            if (location.isEmpty()) {
                model.addAttribute("message", "Invalid bookshelf location!");
                return "add-book";
            }

            Book newBook = new Book();
            newBook.setTitle(title);
            newBook.setType("book");
            newBook.setLocation(bookshelfLocationService.getRandomLocation());

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
    }

    @GetMapping("/books")
    public String books(Model model) {
        List<Book> bookList = bookService.getAllBooks();
        model.addAttribute("books", bookList);
        return "books";
    }

    @GetMapping("/book/{id}")
    public String getBookDetails(@PathVariable Long id, Model model) {
        BibliographicArtifact book = artifactService.getBibliographicArtifactById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
        List<Review> reviews = reviewService.getReviewsForBook(id);
        model.addAttribute("book", book);
        model.addAttribute("reviews", reviews);
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
            newJournal.setType("journal");
            newJournal.setLocation(bookshelfLocationService.getRandomLocation());

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
