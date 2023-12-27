import React ,{useEffect,useState} from "react";
import Header from "./header";
import Footer from "./footer";
import BackToTopButton from "./BackToTopButton";
import courimg from "../static/courimg.jpg"
import { useNavigate } from 'react-router-dom';
import { isExpired} from 'react-jwt'
import VideoCard from "../ApiMethods/videos";
import { VideoList } from "../ApiMethods/videos";
import 'react-html5video/dist/styles.css';


function Courses() {

  const [videoData, setVideoData] = useState([]);
  const navigate = useNavigate();
  const [videos, setVideos] = useState([]);
  

   const token=localStorage.getItem('token')
   const handlePlay =async (video) =>{
    console.log("hndlePlay was called ")
    navigate(`/video/${video.id}`)

  
   }
   
  useEffect(() => {
  
  
    if (!token || isExpired(token)) {
      alert("please login in order to access the courses")
      navigate('/login')

    }else {
      VideoList()
        .then((data) => {
          setVideos(data);
        })
        .catch((error) => {
          console.error('Error fetching videos:', error);
        });
    }
  }, []);
 
  return (
    <div style={{overflowY:"hidden"}}>
      <Header primary={true} fixnav={true} />
     
      <img src={courimg} className="w-100" data-aos="slide-down" />
      <div className="container-fluid ">
        <div className="row courses-heading">
          <h1 className="fs-2 t ms-1" style={{ color: "#171221" }}>
            COURSES
          </h1>
        </div>
       
   
        <div className="row row-cols-1 d-flex justify-content-center p-1 mx-1 ">
         
        
      {videos.map((video) => (
        <VideoCard key={video.id} video={video} onClick={() => handlePlay(video)}/>
      ))}
      
     
        </div>
      </div>
      <BackToTopButton/>
      <Footer/>
    </div>
  );
}

export default Courses;
