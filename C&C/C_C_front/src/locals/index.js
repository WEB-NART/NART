/*
  * @FileDescription: Changing Language Plugin
  * @Author: Zirui Qiao
  * @Date: 2022/12/25 14:14
  * @LastEditor: Zirui Qiao
  * @LastEditTime: 2023/01/07 13:44
*/
import { createI18n } from 'vue-i18n'
import zh from './zh'
import en from './en'

const message = {
  en,
  zh,
}
const language = (navigator.language || 'en').toLocaleLowerCase()
 // get explorer's language
const i18n = createI18n({
  locale: localStorage.getItem('lang') || language.split('-')[0] || 'en', // 设置语言类型
  legacy: false, // if want to support compositionAPI，have to be false;
  globalInjection: true, // global register $t method
  messages: message,
})

export default i18n