import {Route,Routes} from "react-router-dom"
import Login from "./components/login"
import Home from './components/home'
import Courses from './components/courses'
import Tickets from "./components/tickets"
import Services from './components/services/services';
import Devopsconsultancy from './components/services/devopsconsultancy';
import Softwareengineering from './components/services/softwareengineering';
import Videos from "./components/manage/videos";
import Users from "./components/manage/users";
import Manage from "./components/manage/manage";
import VideoDetail from "./components/VideoDetail";
import $ from "jquery";



function App() {
$('[data-aos]').parent().addClass('hideOverflowOnMobile');


  return (
    <div style={{overflowX:"hidden",overflowY:"auto"}}>
       <Routes>
      <Route path="/" element={<Home/>}/>
      <Route path="/login" element={<Login/>}/>
      <Route path="/tickets" element={<Tickets/>}/>
      <Route path="/courses" element={<Courses/>}/>
      <Route path="/video/:videoId" element={<VideoDetail />}/>
      <Route path="/services" element={<Services/>}>
      <Route path="/services/devopsconsultancy" element={<Devopsconsultancy/>}/>
      <Route path="/services/softwareengineering" element={<Softwareengineering/>}/>
    
      </Route>  
      <Route path="/manage" element={<Manage/>}>
      <Route path="/manage/users" element={<Users/>}/>
      <Route path="/manage/videos" element={<Videos/>}/>
        </Route>

      </Routes> 
      
       
    </div>
  );
}

export default App;
