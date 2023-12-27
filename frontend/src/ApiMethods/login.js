import axios from 'axios';
import jwtDecode from 'jwt-decode';
import { useJwt,isExpired,decodeToken } from 'react-jwt'


const Login = async (informations) => {
  try {
    const response = await axios.post('/knops/auth/authenticate', {
      email: informations.email,
      password: informations.password,
    });

    if (response.status==200){
        const token=response.data.token
        const myDecodedToken = decodeToken(token);
        if(myDecodedToken.isValid == false){
          return null
        }
        localStorage.removeItem('token');
        localStorage.setItem('token', token);
       

        return(token)
    }else{
    return(response)

    }

  } catch (error) {
    console.error('Erreur d\'authentification:', error);
  }
};
export default Login;