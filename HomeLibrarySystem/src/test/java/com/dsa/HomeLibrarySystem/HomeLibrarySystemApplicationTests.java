package com.dsa.HomeLibrarySystem;

import com.dsa.HomeLibrarySystem.controller.*;
import com.dsa.HomeLibrarySystem.model.*;
import com.dsa.HomeLibrarySystem.repository.*;
import com.dsa.HomeLibrarySystem.service.*;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class HomeLibrarySystemApplicationTests {

	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private LoginController loginController;

	@Autowired
	private BookshelfLocationRepository bookshelfLocationRepository;

	@Autowired
	private BookRepository bookRepository;


	/**
	 * Test the login method of the LoginController.
	 * Verifies that the view name returned is "login".
	 */
	@Test
	void testLogin() {
		LoginController controller = new LoginController();
		String viewName = controller.login();
		assertEquals("login", viewName);
	}

	/**
	 * Test the logout method of the LoginController.
	 * Verifies that the logout parameter is accessed and
	 * checks if the result redirects to the login page with the logout parameter.
	 */
//	@Test
//	void testLogout() {
//		HttpServletRequest request = mock(HttpServletRequest.class);
//		HttpServletResponse response = mock(HttpServletResponse.class);
//		Authentication authentication = mock(Authentication.class);
//
//		when(request.getParameter("logout")).thenReturn("logout");
//
//		String result = loginController.logout(request, response, authentication);
//
//		verify(request, times(1)).getParameter("logout");
//		assertEquals("redirect:/login?logout", result);
//	}

	/**
	 * Test successful login using the AuthController.
	 * Mocks the member service to return a valid member
	 * and checks that the response status is OK and body matches the member.
	 */
	@Test
	void testLoginSuccess() {
		MemberService memberService = mock(MemberService.class);
		HttpSession session = mock(HttpSession.class);
		Member member = new Member();
		member.setEmail("test@example.com");
		member.setPassword("password");

		when(memberService.authenticate("test@example.com", "password")).thenReturn(Optional.of(member));

		AuthController controller = new AuthController(memberService);
		ResponseEntity<Member> response = controller.login(member, session);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(member, response.getBody());
	}

	/**
	 * Test failed login attempt using the AuthController.
	 * Mocks the member service to return empty for authentication failure
	 * and checks that the response status is UNAUTHORIZED and body is null.
	 */
	@Test
	void testLoginFailure() {
		MemberService memberService = mock(MemberService.class);
		HttpSession session = mock(HttpSession.class);
		Member member = new Member();
		member.setEmail("test@example.com");
		member.setPassword("password");

		when(memberService.authenticate("test@example.com", "password")).thenReturn(Optional.empty());

		AuthController controller = new AuthController(memberService);
		ResponseEntity<Member> response = controller.login(member, session);

		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
		assertNull(response.getBody());
	}

	/**
	 * Test getting the current logged-in user from the session.
	 * Verifies that the response is OK and matches the current user member object.
	 */
	@Test
	void testGetCurrentUserLoggedIn() {
		HttpSession session = mock(HttpSession.class);
		Member member = new Member();
		member.setEmail("test@example.com");

		when(session.getAttribute("currentUser")).thenReturn(member);

		AuthController controller = new AuthController(null);
		ResponseEntity<Member> response = controller.getCurrentUser(session);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(member, response.getBody());
	}

	/**
	 * Test getting the current user when no user is logged in.
	 * Verifies that the response status is UNAUTHORIZED when no current user is found in the session.
	 */
	@Test
	void testGetCurrentUserNotLoggedIn() {
		HttpSession session = mock(HttpSession.class);

		when(session.getAttribute("currentUser")).thenReturn(null);

		AuthController controller = new AuthController(null);
		ResponseEntity<Member> response = controller.getCurrentUser(session);

		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
	}

	/**
	 * Test the availability check of a book.
	 * Verifies that the method returns true when there are available copies
	 * and false when there are no copies available.
	 */
	@Test
	void testCheckAvailability() {
		Book book = new Book();
		book.setAvailableCopies(3);
		assertTrue(book.checkAvailability());

		book.setAvailableCopies(0);
		assertFalse(book.checkAvailability());
	}

	/**
	 * Test the setters and getters for the Author model.
	 * Verifies that the author ID, name, and country are correctly set and retrieved.
	 */
	@Test
	void testAuthorSettersAndGetters() {
		Author author = new Author();
		author.setId(1L);
		author.setName("John Doe");
//		author.setCountry("USA");

		assertEquals(1L, author.getId());
		assertEquals("John Doe", author.getName());
//		assertEquals("USA", author.getCountry());
	}

//	/**
//	 * Test the AuthorRepository for finding an author by name.
//	 */
//	@Test
//	void testAuthorRepository() {
//		Author author = new Author();
//		author.setName("John Doe");
//		authorRepository.save(author);
//		Optional<Author> foundAuthor = authorRepository.findByName("John Doe");
//		assertTrue(foundAuthor.isPresent());
//		assertEquals("John Doe", foundAuthor.get().getName());
//	}

	/**
	 * Test the BookshelfLocationRepository for saving and finding a location.
	 */
	@Test
	void testBookshelfLocationRepository() {
		BookshelfLocation location = new BookshelfLocation(1L, "Fiction", "Top Shelf");
		bookshelfLocationRepository.save(location);
		BookshelfLocation foundLocation = bookshelfLocationRepository.findById(location.getId()).orElse(null);
		assertNotNull(foundLocation, "Expected to find the saved BookshelfLocation but got null.");
		assertEquals(1L, foundLocation.getShelfNumber());
		assertEquals("Fiction", foundLocation.getSection());
		assertEquals("Top Shelf", foundLocation.getPosition());
	}

//	/**
//	 * Test the BookRepository for finding a book by ISBN.
//	 */
//	@Test
//	void testBookRepository() {
//		Book book = new Book();
//		book.setISBN(1234567890123L);
//		book.setTitle("Test Book");
//		bookRepository.save(book);
//		Optional<Book> foundBook = bookRepository.findByISBN(1234567890123L);
//		assertTrue(foundBook.isPresent());
//		assertEquals("Test Book", foundBook.get().getTitle());
//	}

	/**
	 * Test the BookRepository for not finding a book by unknown ISBN.
	 */
	@Test
	void testBookRepository_NotFound() {
		// Act
		Optional<Book> foundBook = bookRepository.findByISBN("9999999999999");

		// Assert
		assertFalse(foundBook.isPresent());
	}
}
