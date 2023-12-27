import React, { useEffect, useState } from "react";
import { UsersList } from "../../ApiMethods/users";
import {  useLocation, useNavigate, Outlet } from "react-router-dom";
import { isExpired, decodeToken } from "react-jwt";
import { Circles } from "react-loader-spinner";
import { addUser, deleteUser } from "../../ApiMethods/users";
import { updateUser } from "../../ApiMethods/users";

export default function Users() {
  const token = localStorage.getItem("token");
  const [username, setUsername] = useState("");
  const [firstname, setFirstname] = useState("");
  const [lastname, setLastname] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [role, setRole] = useState("");
  const [status, setStatus] = useState(null);
  const [loader, setLoader] = useState(false);
  const [authenticated, setauthenticated] = useState(false);
  const [users, setUsers] = useState([]);
  const [loader2,setLoader2]= useState(false)
  const [id,setId]= useState("")
  

  const handleReset = () => {
    setFirstname("");
    setLastname("");
    setEmail("");
    setPassword("");
    setRole("");
    setStatus("");
  };
  const handleValues =  (event,user) => {
    event.preventDefault()
    setEmail(user.email)
    setRole(user.role)
    setId(user.id)
    setStatus(user.valid)
  
  }
  const handleUpdate = async (event) => {
    event.preventDefault();

    setLoader2(true);

    const formData = {
      email: email,
      role: role,
      valid:status
    };

    const result = await updateUser(formData,id);
    alert(result);
    setLoader2(false);
    handleReset();
    UsersList()
    .then((data) => {
      setUsers(data);
      console.log(users);
    })
    .catch((error) => {
      console.error("Error fetching users", error);
    });
  };
  const handleDeleteValues =  (event) => {
    event.preventDefault()
    setEmail("")
    setRole("")
    setId("")
    setStatus(null)
  }

  const handleDelete = (event, id) => {
    const confirmation = window.confirm("are you sure you want to delete ?");
    if (confirmation) {
      event.preventDefault();
      const result = deleteUser(id);
      alert("user successfully deleted");
      UsersList()
      .then((data) => {
        setUsers(data);
      })
      .catch((error) => {
        console.error("Error fetching users", error);
      });
    } else {
      alert("you decided to cancel.");
      return;
    }
  };
  const handleSubmit = async (event) => {
    event.preventDefault();

    setLoader(true);

    const formData = {
      firstname: firstname,
      lastname: lastname,
      email: email,
      password: password,
      role: role,
      status: status,
    };


    const result = await addUser(formData);
    alert("user registered successfully");
    setLoader(false);
    handleReset();
    UsersList()
    .then((data) => {
      setUsers(data);
      console.log(users);
    })
    .catch((error) => {
      console.error("Error fetching users", error);
    });
  };
  const location = useLocation();
  const navigate = useNavigate();
  useEffect(() => {
    if (token && !isExpired(token)) {
      const decoded = decodeToken(token);
      if (decoded.role !== "ADMIN") {
        alert("please login as admin in order to view this page");
        navigate("/login");
      }
      setUsername(decoded.sub);
      setauthenticated(true);
    }
    UsersList()
      .then((data) => {
        setUsers(data);
        console.log(users);
      })
      .catch((error) => {
        console.error("Error fetching users", error);
      });
  }, []);
  return (
    <div className="management-header">
      <div
        class="modal fade"
        id="exampleModal"
        tabindex="-1"
        aria-labelledby="exampleModalLabel"
        aria-hidden="true"
      >
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title" id="exampleModalLabel">
                User addition
              </h5>
              <button
                type="button"
                class="btn-close"
                data-bs-dismiss="modal"
                aria-label="Close"
              ></button>
            </div>
            <div class="modal-body">
              {loader ? (
                <div className="d-flex justify-content-center">
                  {" "}
                  <Circles
                    right="200px"
                    height="80"
                    width="80"
                    radius="9"
                    color="blue"
                    ariaLabel="loading"
                    wrapperStyle
                    wrapperClass
                  />
                </div>
              ) : null}

              <form onSubmit={handleSubmit}>
                <div className="mb-3">
                  <label htmlFor="title" className="form-label">
                    firstname
                  </label>
                  <input
                    type="text"
                    className="form-control"
                    id="title"
                    value={firstname}
                    onChange={(e) => setFirstname(e.target.value)}
                  />
                </div>
                <div className="mb-3">
                  <label htmlFor="description" className="form-label">
                    lastname
                  </label>
                  <input
                    type="text"
                    className="form-control"
                    id="description"
                    value={lastname}
                    onChange={(e) => setLastname(e.target.value)}
                  />
                </div>
                <div className="mb-3">
                  <label htmlFor="description" className="form-label">
                    email
                  </label>
                  <input
                    type="text"
                    className="form-control"
                    id="description"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                  />
                </div>
                <div className="mb-3">
                  <label htmlFor="description" className="form-label">
                    password
                  </label>
                  <input
                    type="password"
                    className="form-control"
                    id="description"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                  />
                </div>
                <div className="mb-3">
                  <label htmlFor="role" className="form-label ">
                    Role &nbsp;:&nbsp;&nbsp;
                  </label>

                  <div className="form-check form-check-inline">
                    <input
                      className="form-check-input"
                      type="radio"
                      name="role"
                      id="admin"
                      value="ADMIN"
                      checked={role === "ADMIN"}
                      onChange={(e) => setRole(e.target.value)}
                    />
                    <label className="form-check-label" htmlFor="admin">
                      Admin
                    </label>
                  </div>
                  <div className="form-check form-check-inline">
                    <input
                      className="form-check-input"
                      type="radio"
                      name="role"
                      id="student"
                      value="STUDENT"
                      checked={role === "STUDENT"}
                      onChange={(e) => setRole(e.target.value)}
                    />
                    <label className="form-check-label" htmlFor="student">
                      Student
                    </label>
                  </div>
                  <br />
                  <div className="mb-3">
                    <label htmlFor="status" className="form-label">
                      Authorized &nbsp;:&nbsp;&nbsp;
                    </label>
                    &nbsp;
                    <div className="form-check form-check-inline">
                      <input
                        className="form-check-input"
                        type="radio"
                        name="status"
                        id="true"
                        value="true"
                        checked={status === 'true'}
                        onChange={() => setStatus('true')}
                      />
                      <label className="form-check-label" htmlFor="true">
                        yes
                      </label>
                    </div>
                    <div className="form-check form-check-inline">
                      <input
                        className="form-check-input"
                        type="radio"
                        name="status"
                        id="false"
                        value="false"
                        checked={status === 'false'}
                        onChange={() => setStatus('false')}
                      />
                      <label className="form-check-label" htmlFor="false">
                        no
                      </label>
                    </div>
                  </div>
                </div>
                <button
                  type="reset"
                  onClick={handleReset}
                  class="btn btn-danger me-1"
                >
                  Reset
                </button>

                <button type="submit" class="btn btn-primary">
                  Save User
                </button>
              </form>
            </div>
            <div class="modal-footer">
              <button
                type="button"
                class="btn btn-secondary"
                data-bs-dismiss="modal"
              >
                Close
              </button>
            </div>
          </div>
        </div>
      </div>
      <div
        class="modal fade"
        id="updateModal"
        tabindex="-1"
        aria-labelledby="exampleModalLabel"
        aria-hidden="true"
        >
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title" id="exampleModalLabel">
                UserUpdate
              </h5>
              <button
                type="button"
                class="btn-close"
                data-bs-dismiss="modal"
                aria-label="Close"
                onClick={handleReset}
              ></button>
            </div>
            <div class="modal-body">
              {loader2 ? (
                <div className="d-flex justify-content-center">
                  {" "}
                  <Circles
                    right="200px"
                    height="80"
                    width="80"
                    radius="9"
                    color="blue"
                    ariaLabel="loading"
                    wrapperStyle
                    wrapperClass
                  />
                </div>
              ) : null}

              <form onSubmit={handleUpdate} >
                
                <div className="mb-3">
                  <label htmlFor="email" className="form-label">
                    email
                  </label>
                  <input
                    type="text"
                    className="form-control"
                    id="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                  />
                </div>
                <label htmlFor="role" className="form-label ">
                    Role &nbsp;:&nbsp;&nbsp;
                  </label>

                  <div className="form-check form-check-inline">
                    <input
                      className="form-check-input"
                      type="radio"
                      name="role"
                      id="admin"
                      value="ADMIN"
                      checked={role === "ADMIN"}
                      onChange={(e) => setRole(e.target.value)}
                    />
                    <label className="form-check-label" htmlFor="admin">
                      Admin
                    </label>
                  </div>
                  <div className="form-check form-check-inline">
                    <input
                      className="form-check-input"
                      type="radio"
                      name="role"
                      id="student"
                      value="STUDENT"
                      checked={role === "STUDENT"}
                      onChange={(e) => setRole(e.target.value)}
                    />
                    <label className="form-check-label" htmlFor="student">
                      Student
                    </label>
                  </div>
                  <br />
                    <label htmlFor="status" className="form-label">
                      Authorized &nbsp;:&nbsp;&nbsp;
                    </label>
                    &nbsp;
                    <div className="form-check form-check-inline">
                      <input
                        className="form-check-input"
                        type="radio"
                        name="status"
                        id="true"
                        value="true"
                        checked={status === true}
                        onChange={(e) => setStatus(e.target.value)}
                      />
                      <label className="form-check-label" htmlFor="true">
                        yes
                      </label>
                    </div>
                    <div className="form-check form-check-inline">
                      <input
                        className="form-check-input"
                        type="radio"
                        name="status"
                        id="false"
                        value="false"
                        checked={status === false}
                        onChange={(e) => setStatus(e.target.value)}
                      />
                      <label className="form-check-label" htmlFor="false">
                        no
                      </label>
                    </div>
                    <br/>
                  
                <button
                  type="reset"
                  onClick={handleReset}
                  class="btn btn-danger me-1"
                >
                  Reset
                </button>

                <button type="submit" class="btn btn-primary">
                  Save Update
                </button>
              </form>
            </div>
            <div class="modal-footer">
              <button
                type="button"
                class="btn btn-secondary"
                 data-bs-dismiss="modal" onClick={(event) => handleDeleteValues(event)}
              >
                Close
              </button>
            </div>
          </div>
        </div>
      </div>
      <div className="container ">
        <div className="d-flex justify-content-end">
          <p
            className="btn btn-primary  ms-auto"
            type="button"
            data-bs-toggle="modal"
            data-bs-target="#exampleModal"
          >
            <i class="bi bi-plus-lg "></i>Add
          </p>
        </div>
        <table class="table table table-bordered ">
          <thead>
            <tr>
              <th scope="col">email</th>
              <th scope="col">role</th>
              <th scope="col">Authorized</th>
              <th scope="col">Options</th>
            </tr>
          </thead>
          <tbody>
            {users.map((user) => (
              <tr key={user.id}>
                <td>{user.email}</td>
                <td>{user.role}</td>
                <td>{user.valid ? "yes" : "No"}</td>
                <td className="options">
                  <div className=" row">
                    <div className="col-xs-12 col-sm-6 col-md-6 col-lg-6 ">
                      <p
                        className="btn btn-outline-info m-1   "
                        data-bs-toggle="modal"
                        data-bs-target="#updateModal"
                         onClick={(event) => handleValues(event, user)}
                      >
                        <i class="bi bi-cloud-download-fill my-1"></i>update
                      </p>
                    </div>
                    <div className="col-xs-12 col-sm-6 col-md-6 col-lg-6 ">
                      <p
                        className="btn btn-outline-danger  m-1 "
                        onClick={(event) => handleDelete(event, user.id)}
                      >
                        <i class="bi bi-trash3"></i>delete
                      </p>
                    </div>
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
