import React ,{useEffect,useState} from "react";
import { Link, useLocation,useNavigate,Outlet } from "react-router-dom";
import d2 from "../../static/d2.jpg";
import Footer from "../footer";
import {  isExpired, decodeToken } from "react-jwt";
import LogoutButton from "../../ApiMethods/logout";



export default function Manage() {
  const token = localStorage.getItem("token");
  const [username, setUsername] = useState("");
  const [authenticated, setauthenticated] = useState(false);

  const location = useLocation();
  const navigate = useNavigate();
  useEffect(() => {
    if (token && !isExpired(token)) {
      const decoded = decodeToken(token);
      if(decoded.role !== "ADMIN"){
        alert("please login as admin in order to view this page")
      navigate('/login')

      }
      setUsername(decoded.sub);
      setauthenticated(true);
    }
  }, []);
  const handleNavigate = (path) => {
    navigate(path);
    console.log(path)
  };

  const imageStyle = {
    width: "4%",
    height: "auto",
    cursor: "pointer", // Ajoute un curseur de type main pour indiquer que l'image est cliquable
  };
  function generateLinkContent ()  {
    if (location.pathname == "/manage/users") {
      return 'Users Management ';
    } else if (location.pathname == "/manage/videos") {
      return 'Videos Management';
    }else
    // Autres cas si nécessaire
    return 'Contenu par défaut';
  };
   
  return (
    <div style={{overflowY:"hidden"}} >
      <nav
        className="navbar navbar-expand-lg fixed-top"
        style={{ backgroundColor: "rgba(0, 0, 0, 0.3)" }}
      >
        <div className="container-fluid">
         
        <img
        src={d2}
        alt="image descriptive du devops"
        className="rounded-pill mx-4 shadow"
        style={imageStyle}
        onClick={() => handleNavigate("/")} 
   
      />
          
          <a
            className="navbar-brand fs-2 text-center mx-auto text-white h1 border-0"
            href=""
          >
            {generateLinkContent()}
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
            <ul className="navbar-nav ms-auto">
              <li className="nav-item navbar-brand text-center  mx-auto text-white h1 border-0 btn btn-outline-primary dropdown">
                <a
                  className="fs-5 btn-outline-primary nav-link text-white dropdown-toggle"
                  role="button"
                  data-bs-toggle="dropdown"
                  aria-expanded="false"
                >
                  Manage
                </a>
                <ul className="dropdown-menu">
                  <li>
                    <Link to="/manage/users" className="dropdown-item btn btn-outline-primary" >
                      Users
                    </Link>
                  </li>
                  <li>
                    <Link to="/manage/videos" className="dropdown-item" >
                      Videos
                    </Link>
                  </li>
                  

                </ul>
              </li>

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
            </ul>
          </div>
        </div>
      </nav>
      <Outlet/>
      <Footer/>
    </div>
  );
}

