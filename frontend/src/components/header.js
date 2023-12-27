import d2 from "../static/d2.jpg";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { useState, useEffect, useReducer } from "react";
import LogoutButton from "../ApiMethods/logout";
import { useJwt, isExpired, decodeToken } from "react-jwt";

function Header(props) {
  const token = localStorage.getItem("token");

  const navigate = useNavigate();
  const [authenticated, setauthenticated] = useState(false);
  const [username, setUsername] = useState("");
  const [userrole, setUserrole] = useState("");

  const handleNavigate = (path) => {
    navigate(path);
  };
  useEffect(() => {
    if (token && !isExpired(token)) {
      const decoded = decodeToken(token);
      setUsername(decoded.sub);
      setUserrole( decoded.role )
      

      console.log(userrole)
      setauthenticated(true);
    }
  }, []);

  return (
    <div>
      <nav
        className={`navbar w-100 navbar-expand-lg  ${
          props.fixnav ? "fixed-top" : ""
        } `}
        style={{ backgroundColor: "rgba(0, 0, 0, 0.3)" }}
      >
        <div className="container-fluid">
          <img
            onClick={() => handleNavigate("/")}
            src={d2}
            alt="image descriptive du devops"
            className="rounded-pill mx-4 shadow "
            style={{ width: "4%", height: "auto" }}
          />

          <a></a>
          <a
            onClick={() => handleNavigate("/")}
            className="navbar-brand text-center mx-auto text-white h1 border-0 btn btn-outline-primary"
            href="#Story"
          >
            K-Ops
          </a>
          <button
            className="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarNavDropdown"
            aria-controls="navbarNavDropdown"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span className="navbar-toggler-icon"></span>
          </button>
          <div className="collapse navbar-collapse " id="navbarNavDropdown">
            <ul className="navbar-nav  ">
              <li className="nav-item  ">
                <Link
                  to="/courses"
                  className={`nav-link border-0 btn ${
                    props.primary ? "btn-primary" : "btn-outline-primary"
                  } text-white h1 fs-5 `}
                  aria-expanded="false"
                >
                  Courses
                </Link>
              </li>
              <li className="nav-item ">
                <a
                 onClick={() => handleNavigate("/tickets")}
                  className="nav-link text-white border-0 btn btn-outline-primary h1 fs-5"
                  aria-current="page"
                  href="#"
                >
                  Tickets
                </a>
              </li>
              <li className="nav-item ">
                <a
                  onClick={() => handleNavigate("/")}
                  className="nav-link text-white h1 btn border-0 btn-outline-primary fs-5"
                  aria-current="page"
                  href="#Services"
                >
                  Services
                </a>
              </li>
              <li className="nav-item  ">
                <a
                  onClick={() => handleNavigate("/")}
                  className="nav-link text-white border-0 btn btn-outline-primary h1 fs-5"
                  href="#Contact"
                >
                  Contact Us
                </a>
              </li>
              {(authenticated && userrole == "ADMIN" )?(
              <li class="nav-item dropdown">
          <a class="nav-link text-white border-0 btn btn-outline-primary h1 fs-5 dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            Manage
          </a>
          <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
            <li><Link to="/manage/videos" class="dropdown-item" >Videos</Link></li>
            <li><Link to="/manage/users" class="dropdown-item" >Users</Link></li>
          </ul>
        </li>):null}
              
            </ul>
            
              {authenticated ? (
            <ul className="navbar-nav ms-auto mt-1">
                
                  <li className="nav-item  ">
                    <a className="nav-link text-white border-0  h5 fs-5">
                       {username}
                    </a>
                  </li>
                  <li className="nav-item   ">
                    <p
                      onClick={LogoutButton}
                      className="  text-white btn btn-outline-primary shadow"
                    >
                      <i className="bi bi-box-arrow-left"></i>
                    </p>
                  </li>
                </ul>
              ) : (
            <ul className="navbar-nav ms-auto">

                <li className="nav-item me-4   ">
                  <Link
                    to="/login"
                    className="nav-link rounded-pill p-2 text-white h1 fs-5 btn btn-outline-primary shadow"
                    href="#"
                  >
                    {" "}
                    Login<i className="bi bi-box-arrow-in-right"></i>
                  </Link>
                </li>
                </ul>
              )}
            
          </div>
        </div>
      </nav>
    </div>
  );
}
export default Header;
