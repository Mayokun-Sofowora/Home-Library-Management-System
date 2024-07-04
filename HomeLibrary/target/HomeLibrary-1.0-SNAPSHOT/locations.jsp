<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
        integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
        crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"
        integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy"
        crossorigin="anonymous"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <title>Locations - Welcome to Home Library</title>
    <link rel="stylesheet" type="text/css" href="styleSearch.css">
</head>

<body>
    <header>
        <nav class="navbar navbar-expand-lg navbar-light rounded">
            <a class="logo-container" href="home.jsp">
                <svg class="logo-content" version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg"
                    xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px" viewBox="0 0 1075 295"
                    style="enable-background:new 0 0 1075 295;" xml:space="preserve">
                    <style type="text/css">
                        .st0 {
                            fill: #ffffff;
                        }
                    </style>
                    <g>
                        <g>
                            <path class="st0"
                                d="M308.8,1.2v136.9h-14.3V74.9h-85.6v63.2h-14.5V1.2h14.5v61h85.6v-61H308.8z" />
                            <path class="st0" d="M376.3,130.1c-11-6.1-19.6-14.4-25.8-24.9c-6.2-10.6-9.3-22.4-9.3-35.6c0-13.2,3.1-25,9.3-35.6
							c6.2-10.6,14.8-18.9,25.8-24.9C387.3,3,399.5,0,413.1,0c13.5,0,25.8,3,36.6,9c10.9,6,19.4,14.3,25.7,24.9
							c6.3,10.6,9.4,22.5,9.4,35.7c0,13.2-3.1,25.1-9.4,35.7c-6.3,10.6-14.8,18.9-25.7,24.9c-10.9,6-23.1,9-36.6,9
							C399.5,139.2,387.3,136.2,376.3,130.1z M442.4,119c8.7-4.9,15.6-11.7,20.5-20.3c4.9-8.7,7.4-18.3,7.4-29c0-10.7-2.5-20.4-7.4-29
							c-5-8.7-11.8-15.4-20.5-20.3c-8.7-4.9-18.5-7.3-29.3-7.3c-10.8,0-20.6,2.4-29.4,7.3c-8.8,4.9-15.7,11.7-20.7,20.3
							c-5,8.7-7.5,18.4-7.5,29c0,10.7,2.5,20.4,7.5,29c5,8.7,11.9,15.4,20.7,20.3c8.8,4.9,18.6,7.3,29.4,7.3
							C423.9,126.3,433.7,123.9,442.4,119z" />
                            <path class="st0"
                                d="M659.3,1.2v136.9h-13.9V28.5l-53.7,92.1h-6.8l-53.7-91.5v108.9h-13.9V1.2h11.9l59.4,101.5L647.4,1.2H659.3z" />
                            <path class="st0"
                                d="M800.6,125.5v12.5h-96.7V1.2h93.8v12.5h-79.3v48.7H789v12.3h-70.7v50.8H800.6z" />
                            <path class="st0" d="M188.4,157.6H220v111.1h68.6v25.8H188.4V157.6z" />
                            <path class="st0" d="M306.4,157.6h31.7v136.9h-31.7V157.6z" />
                            <path class="st0" d="M489.3,235.7c4.4,5.8,6.6,12.9,6.6,21.4c0,12-4.7,21.2-14,27.7c-9.3,6.5-22.9,9.7-40.7,9.7h-70.7V157.6h66.8
							c16.7,0,29.5,3.2,38.4,9.6c8.9,6.4,13.4,15.1,13.4,26c0,6.6-1.6,12.6-4.8,17.8c-3.2,5.2-7.7,9.3-13.4,12.3
							C478.8,225.8,484.9,229.9,489.3,235.7z M402,181.5v32.3h31.5c7.8,0,13.7-1.4,17.8-4.1c4-2.7,6.1-6.8,6.1-12.1c0-5.3-2-9.4-6.1-12
							c-4-2.7-10-4-17.8-4H402z M457.7,266.5c4.2-2.7,6.4-7,6.4-12.7c0-11.3-8.4-17-25.2-17H402v33.8h36.9
							C447.2,270.6,453.5,269.3,457.7,266.5z" />
                            <path class="st0"
                                d="M607.1,294.5l-26.4-38.1h-1.6h-27.6v38.1H520V157.6h59.2c12.1,0,22.6,2,31.6,6.1c8.9,4,15.8,9.8,20.6,17.2
							c4.8,7.4,7.2,16.2,7.2,26.4c0,10.2-2.4,18.9-7.3,26.3c-4.9,7.4-11.8,13-20.8,16.9l30.7,44H607.1z M599.1,189.6
							c-5-4.1-12.2-6.2-21.7-6.2h-25.8v47.7h25.8c9.5,0,16.7-2.1,21.7-6.3c5-4.2,7.4-10,7.4-17.6C606.5,199.6,604.1,193.7,599.1,189.6z" />
                            <path class="st0" d="M753.7,265.2h-63.5l-12.1,29.3h-32.4l61-136.9h31.3L799,294.5h-33.2L753.7,265.2z M743.7,241.1L722,188.7
							l-21.7,52.4H743.7z" />
                            <path class="st0" d="M900.4,294.5L874,256.4h-1.6h-27.6v38.1h-31.7V157.6h59.2c12.1,0,22.6,2,31.6,6.1c8.9,4,15.8,9.8,20.6,17.2
							c4.8,7.4,7.2,16.2,7.2,26.4c0,10.2-2.4,18.9-7.3,26.3c-4.9,7.4-11.8,13-20.8,16.9l30.7,44H900.4z M892.4,189.6
							c-5-4.1-12.2-6.2-21.7-6.2h-25.8v47.7h25.8c9.5,0,16.7-2.1,21.7-6.3c4.9-4.2,7.4-10,7.4-17.6C899.8,199.6,897.4,193.7,892.4,189.6
							z" />
                            <path class="st0"
                                d="M1020.6,246v48.5h-31.7v-48.9l-53-88h33.6l36.5,60.8l36.5-60.8h31.1L1020.6,246z" />
                        </g>
                        <g>
                            <path class="st0" d="M139.4,268.4c-22.1-0.5-44.2-0.9-66.5-1.4c0.3-0.5,0.4-0.9,0.7-1.2c2.4-2.8,4.7-5.6,7.2-8.3
							c0.4-0.5,1.2-0.8,1.8-0.9c10.8-0.6,21.6-0.1,32.3,2.3c8.3,1.9,16.4,4.7,24.1,8.6c0.2,0.1,0.4,0.3,0.6,0.5
							C139.5,268.2,139.4,268.3,139.4,268.4z" />
                            <path class="st0" d="M142.6,260.9c-11.7-5.1-23.8-8.9-36.4-10.5c-5.5-0.7-11-1-16.5-1.5c-2.8-0.2-4-1.2-4.4-4
							c-0.2-1-0.2-2.1-0.2-3.1c0-11.3,0-22.5,0-33.8c0-18.7,0.1-37.4,0.1-56.2c0-1.7,0.5-2.1,2.1-2.1c20.8-0.1,40.2,4.9,58.5,14.8
							c3.2,1.7,4.6,4.4,5.1,7.8c0.3,1.8,0.4,3.6,0.4,5.4c0.1,16,0.1,31.9,0.2,47.9c0,12.3,0.1,24.7,0.2,37v2.5
							C148.3,263.6,145.5,262.2,142.6,260.9z" />
                            <path class="st0" d="M155.5,162.9c-2.1-1.2-4.2-3.4-6.3-4.6c-13.2-7.9-27.6-12.6-42.8-14.8c-8.7-1.3-17.5-1.3-26.3-0.7
							c-1.6,0.1-2.9,0.5-4.1,1.7c-3.8,3.7-8,7.1-11.9,10.7c-1.4,1.3-2,2.7-2,4.8c0.1,18.7,0.1,37.3,0.1,56H62c0,18.4,0,36.9,0,55.3
							c0,1.4,0.1,2.8,0.4,4.1c0.4,1.6,1.3,2.8,3.3,2.8c7,0,14,0,20.9,0c14.8-0.1,29.6-0.2,44.4-0.1c5.7,0,10.9,1.7,14.8,6.3
							c1.7,2,4.1,2.6,6.7,2.7c1,0.1,2,0,3-0.1v7.6H51.9c-3.3-1.2-5.2-3.3-5.2-7c0-45.4,0-90.8,0.1-136.1c0-0.5,0.3-1.2,0.7-1.6
							c3.9-3.6,7.9-7,11.8-10.5c29.9-26.5,59.8-53,89.7-79.5c2.1-1.9,4.3-3.8,6.4-5.7V162.9z" />
                            <path class="st0"
                                d="M155.5,1.4v32.2c-46.2,40.9-92.4,81.8-138.7,122.8c-2.3-2.6-4.5-5.1-6.8-7.6c-2.9-3.3-5.8-6.5-8.7-9.8
							c-0.2-0.3-0.5-0.5-0.8-0.7V138C24,117.3,47.3,96.6,70.7,75.9c25-22.1,49.9-44.2,74.9-66.3c3.1-2.7,6.1-5.5,9.2-8.2H155.5z" />
                            <path class="st0" d="M46.8,79.2c0-19.5,0-38.7,0-58.1c13,0,25.9,0,38.9,0c0,0.4,0.1,0.8,0,1.2c-0.1,7-0.2,14-0.3,21.1
							c0,0.5-0.3,1.2-0.7,1.5C72.2,56.2,59.6,67.6,46.8,79.2z" />
                        </g>
                    </g>
                </svg>
            </a>

            <button class="navbar-toggler collapsed" type="button" data-bs-toggle="collapse"
                data-bs-target="#navbarHeader" aria-controls="navbarSupportedContent" aria-expanded="false"
                aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="mt-2 mt-lg-0 collapse navbar-collapse justify-content-md-center" id="navbarHeader">
                <ul class="navbar-nav">
                    <li class="nav-item dropdown mx-4">
                        <a class="nav-link data-toggle" href="#" role="button" data-bs-toggle="dropdown"
                            aria-expanded="false">Genres</a>
                        <div class="dropdown-menu p-0 multi-column-dropdown" aria-labelledby="dropdownGenres">
                            <a class="dropdown-item" href="/books?genre=Classics">Classics</a>
                            <a class="dropdown-item" href="/books?genre=Romance">Romance</a>
                            <a class="dropdown-item" href="/books?genre=Fiction">Fiction</a>
                            <a class="dropdown-item" href="/books?genre=Detective">Detective</a>
                            <a class="dropdown-item" href="/books?genre=Documentary">Documentary</a>
                            <a class="dropdown-item" href="/books?genre=Biographies">Biographies</a>
                            <a class="dropdown-item" href="/books?genre=History">History</a>
                            <a class="dropdown-item" href="/books?genre=Research">Research</a>
                            <a class="dropdown-item" href="/books?genre=Educational">Educational</a>
                            <a class="dropdown-item" href="/books?genre=Comics">Comics</a>
                            <a class="dropdown-item" href="/books?genre=Manga">Manga</a>
                            <a class="dropdown-item" href="/books?genre=For children">For children</a>
                            <a class="dropdown-item" href="/books?genre=Religion">Religion</a>
                            <a class="dropdown-item" href="/books?genre=Erotic literature">Erotic literature</a>
                            <a class="dropdown-item" href="/books?genre=More...">More...</a>
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
                <form class="form-inline my-2 mx-4 my-lg-0 pl-4">
                    <div class="input-group">
                        <input class="form-control" type="text" id="headerSearch" placeholder="Search"
                            aria-label="Search">
                        <button class="btn btn-outline-secondary header-search-button-custom" type="button">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="#ffffff"
                                class="bi bi-search" viewBox="0 0 16 16">
                                <path
                                    d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001q.044.06.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1 1 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0" />
                            </svg>
                        </button>
                    </div>
                </form>
				<ul class="navbar-nav">
					<li class="nav-item mx-4">
						<a class="nav-link" href="search.jsp">Log Out</a>
					</li>
				</ul>
            </div>
        </nav>
    </header>

    <div class="container">
        <div class="row mt-5">
            <div class="col">
                <h2>Locations</h2>
                <!-- Aquí puedes agregar información sobre las ubicaciones -->
                <table class="table">
                    <thead>
                        <tr>
                            <th scope="col">ID</th>
                            <th scope="col">Shelf Number</th>
                            <th scope="col">Section</th>
                            <th scope="col">Position</th>
                        </tr>
                    </thead>
                    <tbody>
                        <!-- Iterate over the list of locations -->
                        <% for (Location location : (List<Location>) request.getAttribute("locations")) { %>
                        <tr>
                            <td><%= location.getId() %></td>
                            <td><%= location.getShelfNumber() %></td>
                            <td><%= location.getSection() %></td>
                            <td><%= location.getPosition() %></td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>


    
       

    
</body>