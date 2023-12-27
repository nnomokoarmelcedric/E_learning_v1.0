import React, { useState } from "react";
import Devops1 from "../static/Devops1.jpeg";
import d2 from "../static/d2.jpg";
import loginMethod from "../ApiMethods/login";
import { useNavigate } from "react-router-dom";
import { BallTriangle } from "react-loader-spinner";

function Login() {
  const [loader, setLoader] = useState(false);
  const handleNavigate = (path) => {
    // Naviguer vers le chemin spécifié
    navigate(path);
  };
  const [afficherDiv, setAfficherDiv] = useState(false);
  const navigate = useNavigate();
  const handleSubmit = async (e) => {
    setLoader(true)
    e.preventDefault();
    const informaton = {
      email: e.target.email.value,
      password: e.target.password.value,
    };
    const token = await loginMethod(informaton);
    setLoader(false)
    if (token) {
      navigate(-1);
    } else {
      setAfficherDiv(true);
      const timer = setTimeout(() => {
        setAfficherDiv(false);
      }, 5000);
    }
  };
  return (
    <div style={{ color: "#4e49cc" }} data-aos="slide-down">
   
      <div className="container-fluid m-auto conh">
        <div className="row">
          <div className="col-lg-12 text-bg-violet">
            <img
              onClick={() => handleNavigate("/")}
              src={d2}
              alt="image descriptive du devops"
              className="rounded-pill ms-4 mt-3"
              style={{ width: "8%", height: "auto" }}
            />
          </div>
        </div>
      </div>
      {loader?
      <div className="d-flex justify-content-center"> <BallTriangle
        right="200px"
        height="80"
        width="80"
        radius="9"
        color="blue"
        ariaLabel="loading"
        wrapperStyle
        wrapperClass
      /></div>:null}
     
      <div className="container mx-auto ">
        <div className="row p-5 shadow justify-content-center mt-5 mx-2 border rounded border-3 p-2 mb-2 border-opacity-25   mb-2">
          <div class="col-lg-6  border-end border-primary border-4 ">
            <img
              src={Devops1}
              alt="image descriptive du devops"
              className="text-center m-auto"
              style={{ width: "100%", height: "auto" }}
            />
          </div>
       
          <div class="col-lg-6">
            <p className="h1 text-center opacity-75">login</p>
            <div>
              {afficherDiv && (
                <div className="text-danger">incorrect email or password</div>
              )}
            </div>
            <form className="p-3" onSubmit={handleSubmit}>
              <div class="mb-3">
                <label for="exampleInputEmail1" class="form-label">
                  Email address
                </label>
                <input
                  type="email"
                  class="form-control"
                  id="exampleInputEmail1"
                  aria-describedby="emailHelp"
                  name="email"
                  required
                />
              </div>
              <div class="mb-3">
                <label for="exampleInputPassword1" class="form-label">
                  Password
                </label>
                <input
                  type="password"
                  class="form-control"
                  id="exampleInputPassword1"
                  name="password"
                  required
                />
              </div>

              <button
                type="submit"
                class="btn btn-ls btn-outline-primary text-white"
                style={{ backgroundColor: "#4e49cc" }}
              >
                Submit
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}
export default Login;
