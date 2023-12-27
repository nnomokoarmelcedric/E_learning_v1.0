
import React from "react";
import d2 from "../static/d2.jpg";
import { useNavigate } from "react-router-dom";

function Login() {
    const navigate = useNavigate()
  const handleNavigate = (path) => {
    // Naviguer vers le chemin spécifié
    navigate(path);
  };
 
  return (
    <div  data-aos="slide-down">
   
      <div className="container-fluid  m-auto conh">
        <div className="row">
          <div className="col-lg-12 text-bg-violet">
            <img
              onClick={() => handleNavigate("/")}
              src={d2}
              className="rounded-pill ms-4 mt-3"
              style={{ width: "8%", height: "auto" }}
            />
          </div>
        </div>
      </div>
      
     
      <div className="container mx-auto justify-content-center d-flex ">
        <h1>WILL SOON BE AVAILABLE</h1>
      </div>
    </div>
  );
}
export default Login;
