import Vue from 'vue'
import Vuex from 'vuex'
import axios from "axios";

Vue.use(Vuex)

const storage = {
    fetch() {
        const books = [];
        axios.get("/home/recommend")
            .then(res => {
                res.data.forEach(item => books.push(item));
                console.log(this.books);
            });
        return books;
    }
}

export const store = new Vuex.Store({
    state: {
        books: storage.fetch()
    }
});