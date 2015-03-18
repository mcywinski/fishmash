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

ActiveRecord::Schema.define(version: 20150318092251) do

  create_table "languages", force: :cascade do |t|
    t.string   "name"
    t.text     "description"
    t.string   "flag_bitmap_filename"
    t.datetime "created_at",           null: false
    t.datetime "updated_at",           null: false
  end

  create_table "word_list_memberships", force: :cascade do |t|
    t.integer  "word_id"
    t.integer  "word_list_id"
    t.datetime "created_at",   null: false
    t.datetime "updated_at",   null: false
  end

  add_index "word_list_memberships", ["word_id"], name: "index_word_list_memberships_on_word_id"
  add_index "word_list_memberships", ["word_list_id"], name: "index_word_list_memberships_on_word_list_id"

  create_table "word_lists", force: :cascade do |t|
    t.string   "name"
    t.text     "description"
    t.integer  "main_language_id"
    t.integer  "foreign_language_id"
    t.datetime "created_at",          null: false
    t.datetime "updated_at",          null: false
  end

  add_index "word_lists", ["foreign_language_id"], name: "index_word_lists_on_foreign_language_id"
  add_index "word_lists", ["main_language_id"], name: "index_word_lists_on_main_language_id"

  create_table "words", force: :cascade do |t|
    t.string   "phrase"
    t.string   "meaning"
    t.integer  "phrase_language_id"
    t.integer  "meaning_language_id"
    t.datetime "created_at",          null: false
    t.datetime "updated_at",          null: false
  end

  add_index "words", ["meaning_language_id"], name: "index_words_on_meaning_language_id"
  add_index "words", ["phrase_language_id"], name: "index_words_on_phrase_language_id"

end
