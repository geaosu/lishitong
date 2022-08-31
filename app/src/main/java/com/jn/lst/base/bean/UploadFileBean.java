package com.jn.lst.base.bean;

import java.util.List;

/**
 * 上传图片成功后返回的javabean
 */
public class UploadFileBean {

    /**
     * msg : 操作成功
     * code : 200
     * success : true
     * accessory : {"id":"494cc291e4de4b5aad63b413fc050991","createBy":{"id":"47dcaa85a8a64838be127e861802c17a","remarks":"","office":{"id":"3dcb0fb3e7674ea887fd56738307f683","name":"银川市","sort":30,"disabled":false},"duty":"5","loginName":"1001","hkUserId":"5995015","no":"1001","name":"领导角色1001","alphabet":"LINGDAOJIAOJUEJIASESHAI","email":"","phone":"","mobile":"13739550000","photo":"e96ee2154650441a98ded352b28f0e56","sign":"签了个名","officeList":[{"id":"2","sort":30,"disabled":false}],"leaderUserId":"185ac82b935644899431587ba06d5486","leaderUserName":"领导角色1006","roleIds":"b3ee8266af664c9cab01b079b51ae3b7","officeIdList":["2"],"roleIdList":["b3ee8266af664c9cab01b079b51ae3b7"],"admin":false,"roleNames":"领导角色"},"createDate":"2020-10-24 17:21:02","path":"2020/10/24","originalName":"Screenshot_20201024_161126_com.tencent.mm.jpg","type":"image","size":403450}
     */

    private String msg;
    private int code;
    private boolean success;
    private AccessoryBean accessory;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public AccessoryBean getAccessory() {
        return accessory;
    }

    public void setAccessory(AccessoryBean accessory) {
        this.accessory = accessory;
    }

    public static class AccessoryBean {
        /**
         * id : 494cc291e4de4b5aad63b413fc050991
         * createBy : {"id":"47dcaa85a8a64838be127e861802c17a","remarks":"","office":{"id":"3dcb0fb3e7674ea887fd56738307f683","name":"银川市","sort":30,"disabled":false},"duty":"5","loginName":"1001","hkUserId":"5995015","no":"1001","name":"领导角色1001","alphabet":"LINGDAOJIAOJUEJIASESHAI","email":"","phone":"","mobile":"13739550000","photo":"e96ee2154650441a98ded352b28f0e56","sign":"签了个名","officeList":[{"id":"2","sort":30,"disabled":false}],"leaderUserId":"185ac82b935644899431587ba06d5486","leaderUserName":"领导角色1006","roleIds":"b3ee8266af664c9cab01b079b51ae3b7","officeIdList":["2"],"roleIdList":["b3ee8266af664c9cab01b079b51ae3b7"],"admin":false,"roleNames":"领导角色"}
         * createDate : 2020-10-24 17:21:02
         * path : 2020/10/24
         * originalName : Screenshot_20201024_161126_com.tencent.mm.jpg
         * type : image
         * size : 403450
         */

        private String id;
        private CreateByBean createBy;
        private String createDate;
        private String path;
        private String originalName;
        private String type;
        private int size;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public CreateByBean getCreateBy() {
            return createBy;
        }

        public void setCreateBy(CreateByBean createBy) {
            this.createBy = createBy;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getOriginalName() {
            return originalName;
        }

        public void setOriginalName(String originalName) {
            this.originalName = originalName;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public static class CreateByBean {
            /**
             * id : 47dcaa85a8a64838be127e861802c17a
             * remarks :
             * office : {"id":"3dcb0fb3e7674ea887fd56738307f683","name":"银川市","sort":30,"disabled":false}
             * duty : 5
             * loginName : 1001
             * hkUserId : 5995015
             * no : 1001
             * name : 领导角色1001
             * alphabet : LINGDAOJIAOJUEJIASESHAI
             * email :
             * phone :
             * mobile : 13739550000
             * photo : e96ee2154650441a98ded352b28f0e56
             * sign : 签了个名
             * officeList : [{"id":"2","sort":30,"disabled":false}]
             * leaderUserId : 185ac82b935644899431587ba06d5486
             * leaderUserName : 领导角色1006
             * roleIds : b3ee8266af664c9cab01b079b51ae3b7
             * officeIdList : ["2"]
             * roleIdList : ["b3ee8266af664c9cab01b079b51ae3b7"]
             * admin : false
             * roleNames : 领导角色
             */

            private String id;
            private String remarks;
            private OfficeBean office;
            private String duty;
            private String loginName;
            private String hkUserId;
            private String no;
            private String name;
            private String alphabet;
            private String email;
            private String phone;
            private String mobile;
            private String photo;
            private String sign;
            private String leaderUserId;
            private String leaderUserName;
            private String roleIds;
            private boolean admin;
            private String roleNames;
            private List<OfficeListBean> officeList;
            private List<String> officeIdList;
            private List<String> roleIdList;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getRemarks() {
                return remarks;
            }

            public void setRemarks(String remarks) {
                this.remarks = remarks;
            }

            public OfficeBean getOffice() {
                return office;
            }

            public void setOffice(OfficeBean office) {
                this.office = office;
            }

            public String getDuty() {
                return duty;
            }

            public void setDuty(String duty) {
                this.duty = duty;
            }

            public String getLoginName() {
                return loginName;
            }

            public void setLoginName(String loginName) {
                this.loginName = loginName;
            }

            public String getHkUserId() {
                return hkUserId;
            }

            public void setHkUserId(String hkUserId) {
                this.hkUserId = hkUserId;
            }

            public String getNo() {
                return no;
            }

            public void setNo(String no) {
                this.no = no;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAlphabet() {
                return alphabet;
            }

            public void setAlphabet(String alphabet) {
                this.alphabet = alphabet;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public String getLeaderUserId() {
                return leaderUserId;
            }

            public void setLeaderUserId(String leaderUserId) {
                this.leaderUserId = leaderUserId;
            }

            public String getLeaderUserName() {
                return leaderUserName;
            }

            public void setLeaderUserName(String leaderUserName) {
                this.leaderUserName = leaderUserName;
            }

            public String getRoleIds() {
                return roleIds;
            }

            public void setRoleIds(String roleIds) {
                this.roleIds = roleIds;
            }

            public boolean isAdmin() {
                return admin;
            }

            public void setAdmin(boolean admin) {
                this.admin = admin;
            }

            public String getRoleNames() {
                return roleNames;
            }

            public void setRoleNames(String roleNames) {
                this.roleNames = roleNames;
            }

            public List<OfficeListBean> getOfficeList() {
                return officeList;
            }

            public void setOfficeList(List<OfficeListBean> officeList) {
                this.officeList = officeList;
            }

            public List<String> getOfficeIdList() {
                return officeIdList;
            }

            public void setOfficeIdList(List<String> officeIdList) {
                this.officeIdList = officeIdList;
            }

            public List<String> getRoleIdList() {
                return roleIdList;
            }

            public void setRoleIdList(List<String> roleIdList) {
                this.roleIdList = roleIdList;
            }

            public static class OfficeBean {
                /**
                 * id : 3dcb0fb3e7674ea887fd56738307f683
                 * name : 银川市
                 * sort : 30
                 * disabled : false
                 */

                private String id;
                private String name;
                private int sort;
                private boolean disabled;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getSort() {
                    return sort;
                }

                public void setSort(int sort) {
                    this.sort = sort;
                }

                public boolean isDisabled() {
                    return disabled;
                }

                public void setDisabled(boolean disabled) {
                    this.disabled = disabled;
                }
            }

            public static class OfficeListBean {
                /**
                 * id : 2
                 * sort : 30
                 * disabled : false
                 */

                private String id;
                private int sort;
                private boolean disabled;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public int getSort() {
                    return sort;
                }

                public void setSort(int sort) {
                    this.sort = sort;
                }

                public boolean isDisabled() {
                    return disabled;
                }

                public void setDisabled(boolean disabled) {
                    this.disabled = disabled;
                }
            }
        }
    }
}
