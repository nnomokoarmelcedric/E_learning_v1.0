import React,{useEffect} from 'react';
import axios from 'axios';
import play from '../static/devimg2.png'


//video card methode
const VideoCard = ({ video,onClick }) => {
    const value = video.id
    const handleClick = () => {
        onClick(video);}
  return (
    <div  className="card col-lg-3  col-md-12 col-sm-10 zoom m-2 " onClick={handleClick}>
      <img className="card-img-top " src={play} alt={video.name} />
      <div className="card-body">
        <h5 className="card-title">{video.name}</h5>
        <p className="card-text">{video.description}</p>
      </div>
    </div>
  );
};

//videos list methode
const VideoList = async ()=>{
    const token = (localStorage.getItem('token'))
   const response = await axios.get('/videos', {
        headers: {
            Authorization: `${token}` 
        }
    });
    return response.data; 
};

//adding video method
export async function addVideo(video){
    const token = (localStorage.getItem('token'))
    const headers = {
        Authorization: `${token}`, 
      };
    const formData = new FormData();
    formData.append("file", video.video);
    formData.append("name", video.title); 
    formData.append("description", video.description);
    const response = await axios.post('/videos/upload', formData, {
    headers: headers,
  });

    return response.data;
  }
  
  //deleting video methode
  export async function deleteVideo(video){
    const token = (localStorage.getItem('token'))
    const headers = {
        Authorization: `${token}`,
      };
   
    const response = await axios.delete(`/videos/delete/${video}`, {
    headers: headers,
  });
  
    return response.data;
  }
  //updating a video methode 
  export async function updateVideo(data,id){
    const token = (localStorage.getItem('token'))
    const headers = {
        Authorization: `${token}`,
      };
      const formData = new FormData();
      formData.append("name", data.title); 
      formData.append("description", data.description);
   
    const response = await axios.put(`/videos/update/${id}`,formData, {
    headers: headers,
  });
  
    return response.data;
  }
export default VideoCard;
export {VideoList}
