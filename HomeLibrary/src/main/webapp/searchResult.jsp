<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Artifacts - Welcome to Home Library</title>
    <link rel="stylesheet" type="text/css" href="styleSearch.css">
</head>
<body>
<header>
    <nav class="navbar navbar-expand-lg navbar-light rounded">
        <a class="logo-container" href="home.jsp">
            <svg class="logo-content" version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
                 viewBox="0 0 1075 295" style="enable-background:new 0 0 1075 295;" xml:space="preserve">
                <style type="text/css">
                    .st0{fill:#ffffff;}
                </style>
                <!-- Your SVG content here -->
            </svg>
        </a>

        <button class="navbar-toggler collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#navbarHeader" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="mt-2 mt-lg-0 collapse navbar-collapse justify-content-md-center" id="navbarHeader">
            <ul class="navbar-nav">
                <li class="nav-item dropdown mx-4">
                    <a class="nav-link data-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">Genres</a>
                    <div class="dropdown-menu p-0 multi-column-dropdown" aria-labelledby="dropdownGenres">
                        <!-- Dropdown menu items -->
                    </div>
                </li>
                <li class="nav-item active mx-4">
                    <a class="nav-link" href="locations.jsp">Locations</a>
                </li>
                <li class="nav-item mx-4">
                    <a class="nav-link" href="about.jsp">About</a>
                </li>
                <li class="nav-item mx-4">
                    <a class="nav-link" href="contact.jsp">Contact</a>
                </li>
                <li class="nav-item mx-4">
                    <a class="nav-link" href="search.jsp">Filter Search</a>
                </li>
            </ul>
            <form class="form-inline my-2 mx-4 my-lg-0 pl-4" action="SearchServlet" method="get">
                <div class="input-group">
                    <input class="form-control" type="text" id="headerSearch" name="searchString" placeholder="Search" aria-label="Search">
                    <button class="btn btn-outline-secondary header-search-button-custom" type="submit">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="#ffffff" class="bi bi-search" viewBox="0 0 16 16">
                            <!-- Search icon SVG -->
                        </svg>
                    </button>
                </div>
            </form>
            <ul class="navbar-nav">
                <li class="nav-item mx-4">
                    <a class="nav-link" href="logout.jsp">Log Out</a>
                </li>
            </ul>
        </div>
    </nav>
</header>

<main>
    <div class="container mt-4">
        <h2>Search Results</h2>
        <div id="searchResults">
            <%-- Check if searchResult attribute exists in request scope --%>
            <c:if test="${not empty searchResult}">
                <p>Book found: ${searchResult}</p>
            </c:if>
        </div>
    </div>
</main>

<footer>
    <!-- Footer content -->
</footer>

</body>
</html>
