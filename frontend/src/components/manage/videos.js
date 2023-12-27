import React, { useEffect, useState } from "react";
import {
  useLocation,
  useNavigate,
} from "react-router-dom";
import { isExpired, decodeToken } from "react-jwt";
import { VideoList, addVideo, deleteVideo,updateVideo } from "../../ApiMethods/videos";
import {  Circles } from "react-loader-spinner";

export default function Videos() {
  const token = localStorage.getItem("token");
  const [username, setUsername] = useState("");
  const [videos, setVideos] = useState([]);
  const [authenticated, setauthenticated] = useState(false);
  const [videoFile, setVideoFile] = useState(null);
  const [title, setTitle] = useState("");
  const [loader, setLoader] = useState(false);
  const [loader2, setLoader2] = useState(false);
  const [description, setDescription] = useState("");
  const [id, setId] = useState("");
  const handleVideoChange = (event) => {
    const file = event.target.files[0];
    setVideoFile(file);
  };
  const handleReset = () => {
    setVideoFile([]);
    setTitle("");
    setDescription("");
  };
  const handleDelete = (event, id) => {
    const confirmation = window.confirm("are you sure you want to delete ?");
    if (confirmation) {
      event.preventDefault();
      const result = deleteVideo(id);
      alert("video successfully deleted");
      VideoList()
      .then((data) => {
        setVideos(data);
      })
      .catch((error) => {
        console.error("Error fetching videos:", error);
      });
    } else {
      alert("you decided to cancel.");
      return;
    }

    setTitle("");
    setDescription("");
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    setLoader(true);

    const formData = {
      video: videoFile,
      title: title,
      description: description,
    };

    const result = await addVideo(formData);
    alert(result);
    setLoader(false);
    handleReset();
    VideoList()
    .then((data) => {
      setVideos(data);
    })
    .catch((error) => {
      console.error("Error fetching videos:", error);
    });

  };

  const handleValues =  (event,video) => {
    event.preventDefault()
    setTitle(video.name)
    setDescription(video.description)
    setId(video.id)
  }
  const handleDeleteValues =  (event) => {
    event.preventDefault()
    setTitle("")
    setDescription("")
    setId("")
  }
  const handleUpdate = async (event) => {
    event.preventDefault();

    setLoader2(true);

    const formData = {
      title: title,
      description: description,
    };

    
    const result = await updateVideo(formData,id);
    alert(result);
    setLoader2(false);
    handleReset();
    VideoList()
    .then((data) => {
      setVideos(data);
    })
    .catch((error) => {
      console.error("Error fetching videos:", error);
    });
  };

  const location = useLocation();
  const navigate = useNavigate();
  const handlePlay = async (video) => {
    console.log("handlePlay was called ");
    navigate(`/video/${video.id}`);
  };
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
    VideoList()
      .then((data) => {
        setVideos(data);
      })
      .catch((error) => {
        console.error("Error fetching videos:", error);
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
                Video addition
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
                  <label htmlFor="videoFile" className="form-label">
                    Choose the vidéo
                  </label>
                  <input
                    type="file"
                    className="form-control"
                    id="videoFile"
                    onChange={handleVideoChange}
                  />
                </div>
                <div className="mb-3">
                  <label htmlFor="title" className="form-label">
                    Title
                  </label>
                  <input
                    type="text"
                    className="form-control"
                    id="title"
                    value={title}
                    onChange={(e) => setTitle(e.target.value)}
                  />
                </div>
                <div className="mb-3">
                  <label htmlFor="description" className="form-label">
                    Description
                  </label>
                  <input
                    type="text"
                    className="form-control"
                    id="description"
                    value={description}
                    onChange={(e) => setDescription(e.target.value)}
                  />
                </div>
                <button
                  type="reset"
                  onClick={handleReset}
                  class="btn btn-danger me-1"
                >
                  Reset
                </button>

                <button type="submit" class="btn btn-primary">
                  Save Video
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
                VideoUpdate
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

              <form onSubmit={handleUpdate}>
                
                <div className="mb-3">
                  <label htmlFor="title" className="form-label">
                    Title
                  </label>
                  <input
                    type="text"
                    className="form-control"
                    id="title"
                    value={title}
                    onChange={(e) => setTitle(e.target.value)}
                  />
                </div>
                <div className="mb-3">
                  <label htmlFor="description" className="form-label">
                    Description
                  </label>
                  <input
                    type="text"
                    className="form-control"
                    id="description"
                    value={description}
                    onChange={(e) => setDescription(e.target.value)}
                  />
                </div>
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
              <th scope="col">Play</th>
              <th scope="col">name</th>
              <th scope="col">description</th>
              <th scope="col">Options</th>
            </tr>
          </thead>
          <tbody>
            {videos.map((video) => (
              <tr key={video.id}>
                <th scope="row">
                  <i
                    class="bi bi-play-fill h1 play"
                    onClick={() => handlePlay(video)}
                  ></i>
                </th>
                <td>{video.name}</td>
                <td>{video.description}</td>
                <td className="options">
                  <div className=" row">
                    <div className="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                      <p className="btn btn-outline-info m-1   "
                      data-bs-toggle="modal"
                      data-bs-target="#updateModal"
                      onClick={(event) => handleValues(event, video)}>
                        <i class="bi bi-cloud-download-fill my-1"></i>update
                      </p>
                    </div>
                    <div className="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                      <p
                        className="btn btn-outline-danger  m-1 "
                        onClick={(event) => handleDelete(event, video.id)}
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
