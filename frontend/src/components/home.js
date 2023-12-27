import React, { useEffect } from "react";
import { Link } from "react-router-dom";
import c3 from "../static/c3.webp";
import c6 from "../static/c6.jpg";
import c7 from "../static/c7.jpg";
import software from "../static/software.png";
import dev from "../static/dev.jpg";
import consul3 from "../static/consul3.png";
import Footer from "./footer";
import BackToTopButton from "./BackToTopButton";
import Header from "./header";
import { email } from "../ApiMethods/email";

function Home() {
  const handleSubmit = (e) => {
    e.preventDefault();
    const formData = {
      from: e.target.from.value,
      subject: e.target.subject.value,
      text: e.target.text.value,
    };
    email(formData)
      .then((result) => {
        alert("email sent successfully");
        window.location.reload();
      })
      .catch((error) => {
        console.error("Une erreur s'est produite :", error);
      });
  };
  useEffect(() => {
    const urlParams = new URLSearchParams(window.location.search);
    const scrollToSection = urlParams.get("scrollTo");

    if (scrollToSection) {
      const sectionElement = document.getElementById(scrollToSection);
      if (sectionElement) {
        sectionElement.scrollIntoView({ behavior: "smooth" });
      }
    }
  }, []);
  return (
    <div style={{ overflowY: "hidden" }}>
      <Header primary={false} fixnav={true} />
      <div
        id="carouselExampleCaptions"
        class="carousel slide"
        data-bs-ride="carousel"
      >
        <div class="carousel-indicators">
          <button
            type="button"
            data-bs-target="#carouselExampleCaptions"
            data-bs-slide-to="0"
            class="active"
            aria-current="true"
            aria-label="Slide 1"
          ></button>
          <button
            type="button"
            data-bs-target="#carouselExampleCaptions"
            data-bs-slide-to="1"
            aria-label="Slide 2"
          ></button>
          <button
            type="button"
            data-bs-target="#carouselExampleCaptions"
            data-bs-slide-to="2"
            aria-label="Slide 3"
          ></button>
        </div>
        <div class="carousel-inner">
          <div class="carousel-item active">
            <img src={c3} className="img-fluid w-100 h-70" alt="..." />
            <div class="carousel-caption d-none d-md-block">
              <h5>George Washington</h5>
              <p>The harder the conflict, the greater the triumph.</p>
            </div>
          </div>
          <div class="carousel-item">
            <img src={c6} className="img-fluid w-70 h-70" alt="..." />
            <div class="carousel-caption d-none d-md-block">
              <h5>Winston Churchill</h5>
              <p>
                Success is going from failure to failure without losing
                enthusiasm.
              </p>
            </div>
          </div>
          <div class="carousel-item">
            <img src={c7} class="d-block w-100" alt="..." />
            <div class="carousel-caption d-none d-md-block">
              <h5>Steve Jobs</h5>
              <p>
                Your time s limited, don't waste it living someone else's life.
              </p>
            </div>
          </div>
        </div>
        <button
          class="carousel-control-prev"
          type="button"
          data-bs-target="#carouselExampleCaptions"
          data-bs-slide="prev"
        >
          <span class="carousel-control-prev-icon" aria-hidden="true"></span>
          <span class="visually-hidden">Previous</span>
        </button>
        <button
          class="carousel-control-next"
          type="button"
          data-bs-target="#carouselExampleCaptions"
          data-bs-slide="next"
        >
          <span class="carousel-control-next-icon" aria-hidden="true"></span>
          <span class="visually-hidden">Next</span>
        </button>
      </div>

      <div class="container bg-white" id="Story">
        <div
          className="row"
          data-aos="zoom-in-up"
          style={{ overflow: "hidden" }}
        >
          <div className="col-lg-12 d-flex justify-content-center align-items-center">
            <p className="h1 my-5  " style={{ color: "#4e49cc" }}>
              Our Story
            </p>
          </div>
        </div>
        <div className="row " data-aos="zoom-out">
          <div className="col-lg-12  d-flex justify-content-center align-items-center">
            <p className="opacity-75">
              {" "}
              In January 2022, K-Ops was established by KOUEMEKO Hans Aimé, a
              seasoned DevOps specialist, The company, based in Yaoundé,
              Cameroon, swiftly embraced the evolving IT landscape, focusing on
              DevOps to meet the rising demand for optimized software
              development practices. With expertise and a shared technological
              passion, K-Ops swiftly gained recognition in both local and global
              markets. The firm cultivated strategic partnerships, expanding its
              influence in Europe.
              <br /> <br />
              Rapidly, K-Ops drew attention from businesses and students,
              offering online DevOps training. K-Ops efficiently tailored
              solutions for clients, extending beyond training to consulting and
              custom software development. Rooted in client-centered excellence,
              the company solidified its position, becoming a respected name in
              the DevOps domain. Today, K-Ops remains an innovative force,
              committed to advancing technology, and providing quality services
              to foster digital transformation in businesses and empower
              European students through its training programs.
            </p>
          </div>
        </div>
        <div className="row mt-4 mb-3 d-flex justify-content-center align-items-center">
          <div className="col-lg-6 shadow " data-aos="slide-up">
            <p className="h3 text-center opacity-75 text-primary m-4 ">
              MISSION
            </p>
            <p className="text-center m-2">
              Empower businesses with efficient DevOps solutions for streamlined
              digital transformations.
            </p>
          </div>

          <div
            className="col-lg-6 shadow  "
            data-aos="slide-down"
            style={{ backgroundColor: "#d7d8d9" }}
          >
            <p className="h3 text-center opacity-75 text-primary m-4">
              {" "}
              VISION
            </p>
            <p className="text-center m-2">
              Lead as a catalyst in digital innovation, bridging excellence for
              sustainable growth.
            </p>
          </div>
        </div>
      </div>
      <div className="container-fluid mb-1 py-2 bg-light " id="Services">
        <div className="row">
          <div
            className="col-lg-12 d-flex  justify-content-center align-items-center"
            data-aos="zoom-in"
            style={{ backgroundColor: "#4e49cc" }}
          >
            <p className="h1 my-5 text-white" style={{ color: "#4e49cc" }}>
              Our Services
            </p>
          </div>
        </div>
        <div className="row d-flex justify-content-center align-items-center">
          <div
            className="col-lg-3   d-flex justify-content-center align-items-center"
            data-aos="flip-up"
          >
            <div class="card zoom  ">
              <img src={consul3} class="card-img-top w-100 h-150" alt="..." />
              <div class="card-body">
                <h5 class="card-title">DevOps Consultancy</h5>
                <p class="card-text">
                  K-Ops excels in DevOps consulting, enhancing your development
                  and operational workflows for peak efficiency and seamless
                  delivery.
                </p>
                <Link
                  to="/services/devopsconsultancy"
                  href="#"
                  class="btn w-100 btn-outline-primary"
                >
                  learn more
                </Link>
              </div>
            </div>
          </div>
          <div
            className="col-lg-3 d-flex justify-content-center align-items-center"
            data-aos="flip-down"
          >
            <div class="card zoom ">
              <img src={software} class="card-img-top w-100 h-150" alt="..." />
              <div class="card-body">
                <h5 class="card-title">Software engineering</h5>
                <p class="card-text">
                  K-Ops provides high-quality software engineering solutions,
                  forging strong, innovative, and growth-driven software.
                </p>
                <Link
                  to="/services/softwareengineering"
                  href="#"
                  class="btn w-100 btn-outline-primary"
                >
                  Learn more
                </Link>
              </div>
            </div>
          </div>

          <div
            className="col-lg-3 d-flex justify-content-center align-items-center"
            data-aos="flip-right"
          >
            <div class="card  zoom ">
              <img src={dev} class="card-img-top w-100 h-150" alt="..." />
              <div class="card-body">
                <h5 class="card-title">DevOps Course</h5>
                <p class="card-text">
                  K-Ops offers extensive DevOps training, equipping individuals
                  with crucial skills for efficient, collaborative software
                  delivery and operations.
                </p>
                <Link
                  to="courses"
                  href="#"
                  class="btn w-100 btn-outline-primary"
                >
                  Learn more
                </Link>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div
        className="row bg  d-flex justify-content-center align-items-center shadow"
        id="Contact"
        data-aos="slight-left"
      >
        <div className="col-lg-12 d-flex justify-content-center ">
          <p className="h1 " style={{ color: "#4e49cc" }}>
            Contact Us
          </p>
        </div>
        <div className="col-lg-5 ">
          <form onSubmit={handleSubmit}>
            <div className="mb-3">
              <span className="h2 d-block mb-2 ms-2">Get in touch</span>
              <div className="input-group w-50 m-2">
                <input
                  type="text"
                  className="form-control"
                  id="nameInput"
                  placeholder="Name"
                  required
                />
              </div>
              <div className="input-group w-50 m-2">
                <input
                  type="email"
                  className="form-control"
                  id="emailInput"
                  aria-describedby="emailHelp"
                  placeholder="Email"
                  name="from"
                  required
                />
              </div>

              <div id="emailHelp" className="form-text m-2">
                We'll never share your email with anyone else.
              </div>
            </div>
            <div className="m-2">
              <input
                type="text"
                className="form-control"
                id="subjectInput"
                placeholder="Subject"
                name="subject"
                required
              />
            </div>
            <div className="m-2">
              <textarea
                rows={5}
                className="form-control"
                id="messageInput"
                placeholder="Message"
                name="text"
                required
              />
            </div>

            <button type="submit" className="btn btn-dark m-2">
              Submit
            </button>
          </form>
        </div>
      </div>
      <BackToTopButton />

      <Footer />
    </div>
  );
}
export default Home;
