# encoding: UTF-8
# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended that you check this file into your version control system.

ActiveRecord::Schema.define(version: 20150524005711) do

  # These are extensions that must be enabled in order to support this database
  enable_extension "plpgsql"

  create_table "answers", force: :cascade do |t|
    t.integer  "word_id"
    t.integer  "user_id"
    t.integer  "assesment_id"
    t.string   "answer"
    t.boolean  "passed"
    t.boolean  "finished"
    t.datetime "created_at",   null: false
    t.datetime "updated_at",   null: false
  end

  add_index "answers", ["assesment_id"], name: "index_answers_on_assesment_id", using: :btree
  add_index "answers", ["user_id"], name: "index_answers_on_user_id", using: :btree
  add_index "answers", ["word_id"], name: "index_answers_on_word_id", using: :btree

  create_table "api_tokens", force: :cascade do |t|
    t.string   "token"
    t.integer  "user_id"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  add_index "api_tokens", ["user_id"], name: "index_api_tokens_on_user_id", using: :btree

  create_table "assesments", force: :cascade do |t|
    t.integer  "exam_id"
    t.integer  "user_id"
    t.datetime "time_started"
    t.datetime "created_at",   null: false
    t.datetime "updated_at",   null: false
    t.boolean  "finished"
  end

  add_index "assesments", ["exam_id"], name: "index_assesments_on_exam_id", using: :btree
  add_index "assesments", ["user_id"], name: "index_assesments_on_user_id", using: :btree

  create_table "exams", force: :cascade do |t|
    t.string   "name"
    t.text     "description"
    t.date     "date_practice_start"
    t.date     "date_practice_finish"
    t.date     "date_exam_start"
    t.date     "date_exam_finish"
    t.integer  "word_count"
    t.datetime "created_at",           null: false
    t.datetime "updated_at",           null: false
  end

  create_table "languages", force: :cascade do |t|
    t.string   "name"
    t.text     "description"
    t.string   "flag_bitmap_filename"
    t.datetime "created_at",           null: false
    t.datetime "updated_at",           null: false
  end

  create_table "student_class_memberships", force: :cascade do |t|
    t.integer  "user_id"
    t.integer  "student_class_id"
    t.datetime "created_at",       null: false
    t.datetime "updated_at",       null: false
  end

  add_index "student_class_memberships", ["student_class_id"], name: "index_student_class_memberships_on_student_class_id", using: :btree
  add_index "student_class_memberships", ["user_id"], name: "index_student_class_memberships_on_user_id", using: :btree

  create_table "student_classes", force: :cascade do |t|
    t.string   "name"
    t.text     "description"
    t.datetime "created_at",  null: false
    t.datetime "updated_at",  null: false
  end

  create_table "users", force: :cascade do |t|
    t.string   "login"
    t.string   "email"
    t.string   "password_digest"
    t.integer  "user_type"
    t.boolean  "active"
    t.string   "activation_token"
    t.datetime "created_at",       null: false
    t.datetime "updated_at",       null: false
  end

  create_table "word_list_exams", force: :cascade do |t|
    t.integer  "exam_id"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.integer  "word_list_id"
  end

  create_table "word_list_memberships", force: :cascade do |t|
    t.integer  "word_id"
    t.integer  "word_list_id"
    t.datetime "created_at",   null: false
    t.datetime "updated_at",   null: false
  end

  add_index "word_list_memberships", ["word_id"], name: "index_word_list_memberships_on_word_id", using: :btree
  add_index "word_list_memberships", ["word_list_id"], name: "index_word_list_memberships_on_word_list_id", using: :btree

  create_table "word_lists", force: :cascade do |t|
    t.string   "name"
    t.text     "description"
    t.integer  "main_language_id"
    t.integer  "foreign_language_id"
    t.datetime "created_at",          null: false
    t.datetime "updated_at",          null: false
  end

  add_index "word_lists", ["foreign_language_id"], name: "index_word_lists_on_foreign_language_id", using: :btree
  add_index "word_lists", ["main_language_id"], name: "index_word_lists_on_main_language_id", using: :btree

  create_table "words", force: :cascade do |t|
    t.string   "phrase"
    t.string   "meaning"
    t.integer  "phrase_language_id"
    t.integer  "meaning_language_id"
    t.datetime "created_at",          null: false
    t.datetime "updated_at",          null: false
  end

  add_index "words", ["meaning_language_id"], name: "index_words_on_meaning_language_id", using: :btree
  add_index "words", ["phrase_language_id"], name: "index_words_on_phrase_language_id", using: :btree

end
