import React from "react";
import { useEffect,useState } from "react";


function BackToTopButton() {

    const [backToTopButton,setBackToTopButton] = useState(false)

    useEffect(()=>{
        window.addEventListener("scroll", () =>{
            if(window.scrollY >600)
            setBackToTopButton(true)
        else{
            setBackToTopButton(false)
        }
        })
    },[])

    const scrollUp = ()=>{
        window.scrollTo({
            top: 0,
            behavieur: "smooth"
        })
    }


    return (
      <div>
      {backToTopButton && (
        <button className="rounded-pill btn btn-outline-dark" style={{ position:"fixed",bottom:"20px",right:"30px",height:"50px",width:"50px",fontSize:"50px"}} onClick={scrollUp}>^</button>

      )}
        
      </div>
    );
  }
  
  export default BackToTopButton;
  