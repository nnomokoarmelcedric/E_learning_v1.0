import axios from 'axios';

export function email(informations){
  return axios.post('/knops/auth/send-email',{
    from:informations.from,
    to:"nnomoko.armel20@myiuc.com",
    subject:informations.subject,
    text:informations.text
  })
  .then(response=>response.data)
}