<template>
  <div>
    <div class="text-center">
      <label for="file">
        <img src="@/assets/images/no-image-2.png" alt="" style="width: 250px; height: 400px; border-radius: 7px;">
      </label>
    </div>
    <div class="filebox">
      <input class="upload-file" value="첨부파일" placeholder="첨부파일">
      <label for="file">파일찾기</label>
      <input type="file" id="file">
    </div>
    <div id="editorjs"></div>
  </div>
</template>

<script>
import EditorJS from "@editorjs/editorjs";

const editor = new EditorJS({
  autofocus: true
});
editor.isReady.then(() => {
  console.log('editor is ready to work!');
})
export default {
  name: "Edit"
}


</script>

<style scoped>
  .text-center {
    text-align: center;
  }
  .filebox .upload-file {
    display: inline-block;
    height: 40px;
    padding: 0 10px;
    vertical-align: middle;
    border: 1px solid #dddddd;
    width: 78%;
    color: #999999;
  }
  .filebox label {
    display: inline-block;
    padding: 10px 20px;
    color: #fff;
    vertical-align: middle;
    background-color: #999999;
    cursor: pointer;
    height: 40px;
    margin-left: 10px;
  }
  .filebox input[type="file"] {
    position: absolute;
    width: 0;
    height: 0;
    padding: 0;
    overflow: hidden;
    border: 0;
  }
</style>