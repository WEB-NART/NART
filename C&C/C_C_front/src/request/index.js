/*
  * @FileDescription: axios HTTP request general function
  * @Author: Zirui Qiao
  * @Date: 2022/12/25 14:13
  * @LastEditor: Zirui Qiao
  * @LastEditTime: 2023/01/01 21:56
*/
import axios from 'axios'
import { url } from './token'

axios.defaults.withCredentials = true;
const req = axios.create({
    baseURL: 'http://' + url,
    timeout: 10000
});
export default req