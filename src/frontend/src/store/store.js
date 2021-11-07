import Vue from 'vue'
import Vuex from 'vuex'
import axios from "axios";

Vue.use(Vuex)

const storage = {
    recommend() {
        const books = [];
        axios.get("/home/recommend")
            .then(res => {
                res.data.forEach(item => books.push(item));
            });
        return books;
    },
    newest() {
        const books = [];
        axios.get("/home/newest")
            .then(res => {
                res.data.forEach(item => books.push(item));
            });
        return books;
    }
}

export const store = new Vuex.Store({
    state: {
        books: storage.recommend(),
        newest: storage.newest(),
        bookDetail: storage.detail()
    },
    actions: {
        detail(commit, isbn) {
            let bookDetail = null;
            axios.get("/detail", {params: {isbn: isbn}})
                .then(res => bookDetail = res.data)
            return bookDetail;
        }
    }
});