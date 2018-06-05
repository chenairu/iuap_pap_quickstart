var meta = {
  meta: {
    id: {
      type: "string",
      required: true,
      notipFlag: true,
      hasSuccess: true,
      nullMsg: "机构编码不能为空!"
    },
    pid: {
      type: "string",
      required: true,
      notipFlag: true,
      hasSuccess: true,
      nullMsg: "机构名称不能为空!"
    },
    name: {
      type: "string"
    },
    flag: {
      type: "string"
    }
  }
}
