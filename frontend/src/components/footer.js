import React from "react";
import { Link } from "react-router-dom";

function footer() {
  return (
    <div data-aos="fade-up">
      <div
        className="text-white pt-5 pb-4"
        style={{ backgroundColor: "#4e49cc" }}
      >
        <div className="container text-center text-md-left">
          <div className="row text-center text-md-left">
            <div className="col-md-3 col-lg-3 col-xl-3 mx-auto mt-3 text-start">
              <h5 className="text-uppercase mb-4 font-weight-bold text-warning">
                K-ops
              </h5>
              <p className="justify-content-start">
              "K-ops: Your partner in innovative technology solutions,
               simplifying complexities and providing tailored solutions for every need."
              </p>
            </div>
            <div className="col-md-2 col-lg-2 col-xl-2 mx-auto mt-3 text-start">
              <h5 className="text-uppercase mb-4 font-weight-bold text-warning">
                Services
              </h5>
              <p>
                <Link to="/services/devopsconsultancy"
                  href="#"
                  className="text-white"
                  style={{ textDecoration: "none" }}
                >
                  DevOps Consultancy
                </Link>
              </p>
              <hr/>
              <p>
                <Link
                to="/services/softwareengineering"
                  href="#"
                  className="text-white"
                  style={{ textDecoration: "none" }}
                >
                  Software Engineering
                </Link>
              </p>
              <hr/>
              <p>
                <Link
                to="/courses"
                  href="#"
                  className="text-white"
                  style={{ textDecoration: "none" }}
                >
                  DevOps Courses
                </Link>
              </p>
          
            </div>
            
            <div className="col-md-4 col-lg-3 col-xl-3 mx-auto mt-3 text-start">
              <h5 className="text-uppercase mb-4 font-weight-bold text-warning">
                Contact
              </h5>
              <p>
                <i class="bi bi-house-fill me-3"></i>Yaoundé, 00237 CMR
              </p>
              <p>
                <i class="bi bi-envelope-at me-3"></i>contact@Kops.dev
              </p>
              <p>
                <i class="bi bi-whatsapp me-3"></i>+49 17 66 53 19 417
              </p>
              <p>
                <i class="bi bi-telephone me-3"></i>+237 6 57 02 37 30
              </p>
            </div>
          </div>
          <hr className="mb-4" />
          <div className="row align-items-center text-start">
            <div className="col-md-7 col-lg-8">
              <p>
                {" "}
                Copyright © 2023 kops.dev. All Rights Reserved. - Legal notice
                - FAQ{" "}
                <a href="#"  style={{ textDecoration: "none" }}>
                  <strong className="text-warning" >K-Ops</strong>
                </a>
              </p>
            </div>
            <div className="col-md-5 col-lg-4">
                <div className="text-center text-md-right">
                    <ul className="list-unstyled list-inline">
                        <li className="list-inline-item">
                        <a href="#" class="btn-floating btn-sm text-white" style={{fontSize:"23px"}}>
                        <i class="bi bi-facebook m-2"></i>
                        </a>
                        </li>
                        <li className="list-inline-item">
                        <a href="#" class="btn-floating btn-sm text-white" style={{fontSize:"23px"}}>
                        <i class="bi bi-whatsapp m-2"></i>
                        </a>
                        </li>
                        <li className="list-inline-item">
                        <a href="#" class="btn-floating btn-sm text-white" style={{fontSize:"23px"}}>
                        <i class="bi bi-instagram m-2"></i>
                        </a>
                        </li>
                        <li className="list-inline-item">
                        <a href="#" class="btn-floating btn-sm text-white" style={{fontSize:"23px"}}>
                        <i class="bi bi-twitter m-2"></i>
                        </a>
                        </li>
                    </ul>

                </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
export default footer;
