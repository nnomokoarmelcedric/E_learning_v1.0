import axios from "axios"

  //listing the videos

  export const UsersList = async ()=>{
    const token = (localStorage.getItem('token'))
   const response = await axios.get('/knops/auth/users', {
        headers: {
            Authorization: `${token}` 
        }
    });
    return response.data; 
};
//adding videos
export async function addUser(user){
    const token = (localStorage.getItem('token'))
    const headers = {
        Authorization: `${token}`, 
      };
      const userData = {
        firstname: user.firstname,
        lastname: user.lastname,
        email: user.email,
        password: user.password,
        role: user.role,
        valid: user.status,
      };
    const response = await axios.post('/knops/auth/register',  {
        firstname: user.firstname,
        lastname: user.lastname,
        email: user.email,
        password: user.password,
        role: user.role,
        valid: user.status,
    headers: headers,
   
  });
    
    return response.data;
  }
  //deleting a user
    export async function deleteUser(video){
      const token = (localStorage.getItem('token'))
      const headers = {
          Authorization: `${token}`,
        };
     
      const response = await axios.delete(`/knops/auth/delete/${video}`, {
      headers: headers,
    });
      return response.data;
    }
    //updating a user
    export async function updateUser(data,id){
      const token = (localStorage.getItem('token'))
      const headers = {
          Authorization: `${token}`,
        };
        const formData = new FormData();
        formData.append("email", data.email); 
        formData.append("role", data.role);
        formData.append("status", data.valid);
     
      const response = await axios.patch(`/knops/auth/user/update/${id}`,formData, {
      headers: headers,
    });
    
      return response.data;
    }