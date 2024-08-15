<template>
    <v-dialog>
        <template v-slot:activator="{ props: activatorProps }">
            <v-btn v-bind="activatorProps" color="surface-variant" text="Open Dialog" variant="flat">+</v-btn>
        </template>
        <template v-slot:default="{ isActive }">
            <v-card title="Dialog">
                <v-card-text>
                    Create New Followed Topic
                    <v-text-field v-model="newFollowedTopic.name" label="Followed Topic Name"></v-text-field>

                    <v-text-field @keyup="searchTopics" @click:clear="onClearEvent" clearable v-model="name"
                        label="Search Topic..."></v-text-field>
                   
                    <v-row>
                        <v-col>
                            Topics
                            <v-card @click="addToFollowedTopicTopic(topic)" v-for="(topic, index) in searchTopicList"
                                :key="index" class="ma-2" prepend-icon="$vuetify" variant="outlined">

                                <template v-slot:title>
                                    <span v-if="topic.namedEntityRecognitionType === 'loc'" class="font-weight-black">
                                        Locations
                                    </span>
                                    <span v-if="topic.namedEntityRecognitionType === 'misc'" class="font-weight-black">
                                        Miscellaneous</span>
                                    <span v-if="topic.namedEntityRecognitionType === 'per'" class="font-weight-black">
                                        Persons
                                    </span>
                                    <span v-if="topic.namedEntityRecognitionType === 'org'" class="font-weight-black">
                                        Organisations
                                    </span>
                                </template>
                                <v-card-text class="bg-surface-light pt-4">
                                    {{ topic.name }}
                                </v-card-text>
                            </v-card>
                        </v-col>
                        
                        <v-col>
                            Topics selected
                            <v-card  v-for="(topic, index) in newFollowedTopic.topics"
                                :key="index" class="ma-2" prepend-icon="$vuetify" variant="outlined">

                                <template v-slot:title>
                                    <span v-if="topic.namedEntityRecognitionType === 'loc'" class="font-weight-black">
                                        Locations
                                    </span>
                                    <span v-if="topic.namedEntityRecognitionType === 'misc'" class="font-weight-black">
                                        Miscellaneous</span>
                                    <span v-if="topic.namedEntityRecognitionType === 'per'" class="font-weight-black">
                                        Persons
                                    </span>
                                    <span v-if="topic.namedEntityRecognitionType === 'org'" class="font-weight-black">
                                        Organisations
                                    </span>
                                </template>
                                <v-card-text class="bg-surface-light pt-4">
                                    {{ topic.name }}
                                </v-card-text>
                            </v-card> 
                        </v-col>
                    </v-row>
                    <v-btn @click="createNewFollowedTopic">create Topic</v-btn>
                </v-card-text>

                <v-card-actions>
                    <v-spacer></v-spacer>

                    <v-btn text="Close Dialog" @click="isActive.value = false"></v-btn>
                </v-card-actions>
            </v-card>
        </template>
    </v-dialog>

    <v-col>
        <v-row>
            <v-card min-width="300" max-width="300" v-for="(topic, index) in followedTopics" :key="index" class="ma-2"
                prepend-icon="mdi-chart-box" variant="outlined">
                <template v-slot:title>
                    <span class="font-weight-black">
                        {{ topic.name }} </span>
                </template>
                <v-card-text class="bg-surface-light pt-4">
                    <v-dialog>
                        <template v-slot:activator="{ props: activatorProps }">
                            <div class="position-absolute bottom-0 right-0">
                                <v-btn @click="showFollowedTopicDetails(topic)" v-bind="activatorProps"
                                    color="surface-variant" text="Open Dialog" variant="flat"><v-icon class="ma-2"
                                        aria-hidden="false"> mdi-magnify
                                    </v-icon></v-btn>
                            </div>

                        </template>
                        <template v-slot:default="{ isActive }">
                            <v-card title="Followed Topic Overview">
                                <v-card-text>
                                    {{ showFollowedTopicDetailsVar.name }}
                                    <v-card v-for="(topic, index) in showFollowedTopicDetailsVar.topics" :key="index"
                                        class="ma-2 pa-2">
                                        {{ topic.name }} ({{ topic.namedEntityRecognitionType }})

                                    </v-card>

                                    <div id="chart">
                                        <apexchart type="line" height="350" :options="chartData.chartOptions"
                                            :series="chartData.series">
                                        </apexchart>
                                    </div>

                                </v-card-text>

                                <v-card-actions>
                                    <v-spacer></v-spacer>

                                    <v-btn text="Close Dialog" @click="isActive.value = false"></v-btn>
                                </v-card-actions>
                            </v-card>
                        </template>
                    </v-dialog>
                </v-card-text>

            </v-card>

        </v-row>
    </v-col>
</template>

<script>
export default {

    data() {
        return {
            chartData: {
                series: [
                    {
                        name: "Positiv",
                        data: []
                    },
                    {
                        name: "Negativ",
                        data: []
                    },
                    {
                        name: "Neutral",
                        data: []
                    }
                ],

                chartOptions: {
                    chart: {
                        height: 350,
                        type: 'line',
                        dropShadow: {
                            enabled: true,
                            color: '#000',
                            top: 18,
                            left: 7,
                            blur: 10,
                            opacity: 0.2
                        },
                        zoom: {
                            enabled: false
                        },
                        toolbar: {
                            show: false
                        }
                    },
                    colors: ['#77B6EA', '#545454', '#116464'],
                    dataLabels: {
                        enabled: true,
                    },
                    stroke: {
                        curve: 'smooth'
                    },
                    title: {
                        text: 'Average Sentiment Positive, Negative and Neutral',
                        align: 'left'
                    },
                    grid: {
                        borderColor: '#e7e7e7',
                        row: {
                            colors: ['#f3f3f3', 'transparent'], // takes an array which will be repeated on columns
                            opacity: 0.5
                        },
                    },
                    markers: {
                        size: 1
                    },
                    xaxis: {
                        categories: [],
                        title: {
                            text: ''
                        }
                    },
                    yaxis: {
                        title: {
                            text: 'Sentiment'
                        },
                        min: 0,
                        max: 1
                    },
                    legend: {
                        position: 'top',
                        horizontalAlign: 'right',
                        floating: true,
                        offsetY: -25,
                        offsetX: -5
                    }
                },
            },

            newFollowedTopic: {},
            dates: { timestampStart: 0, timestampEnd: 0 },
            host: process.env.VUE_APP_BASE_URL,
            nerTypes: ["loc", "misc", "per", "org"],
            currentDate: new Date().toISOString().split('T')[0],//"2024-07-28",
            name: "",
            searchTopicList: [],
            followedTopics: [],
            testi: "",
            showFollowedTopicDetailsVar: {},
        }
    },
    mounted: function () {
        this.searchTopics();
        this.getAllFollowedTopics();
        this.initNewFollowedTopic();
    },


    computed: {
    },

    methods: {
        initNewFollowedTopic() {
            this.newFollowedTopic = {
                "id": -1,
                "name": "new topic " + Math.floor(Math.random() * 10000),
                "topics": []
            };
        },

        showFollowedTopicDetails(topic) {
            this.showFollowedTopicDetailsVar = topic;
            this.chartData.series[0]["data"] = [];
            this.chartData.series[1]["data"] = [];
            this.chartData.series[2]["data"] = [];
            this.chartData.chartOptions.xaxis.categories = [];

            for (let x of this.showFollowedTopicDetailsVar.followedTopicSentimentByDay) {
                console.log();
                this.chartData.series[0]["data"].push(x["positive"].toFixed(2));
                this.chartData.series[1]["data"].push(x["negative"].toFixed(2));
                this.chartData.series[2]["data"].push(x["neutral"].toFixed(2));
                this.chartData.chartOptions.xaxis.categories.push(x["date"]);
            }
        },
        addToFollowedTopicTopic(e) {
            let index = this.searchTopicList.indexOf(e);
            if (index > -1) { // only splice array when item is found
                this.searchTopicList.splice(index, 1); // 2nd parameter means remove one item only
                this.newFollowedTopic.topics.push(e);
            }
        },

        onClearEvent() {
            console.log("cleared");
            this.searchTopicList = [];
        },


        async createNewFollowedTopic() {

            let requestOptions = {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(this.newFollowedTopic),
            };


            let res = await fetch(this.host + "/api/followedTopic/create", requestOptions);
            this.followedTopics.push((await (res.json())));
            console.log(res.ok);
            if (res.ok === true) {
                this.initNewFollowedTopic();
            }
        },

        async getAllFollowedTopics() {
            let res = await fetch(this.host + "/api/followedTopic/getAll");
            this.followedTopics = (await (res.json()));
        },

        async searchTopics() {
            try {
                //today
                if (this.name !== "" && this.name.length >= 2) {
                    let res = await fetch(this.host + "/api/topic/like/" + this.name);
                    this.searchTopicList = (await (res.json()));
                }
            } catch {
                console.log("Error fetching");
                this.error = "Error fetching";
            }
        }
    }
}
</script>